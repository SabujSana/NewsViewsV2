package com.greendreamlimited.newsviewsv2.fragments;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.greendreamlimited.newsviewsv2.Models.NewsSource.Article;
import com.greendreamlimited.newsviewsv2.Models.NewsSource.NewsSource;
import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.activities.DetailsNewsActivity;
import com.greendreamlimited.newsviewsv2.activities.HomeActivity;
import com.greendreamlimited.newsviewsv2.adapters.NewsViewAdapter;
import com.greendreamlimited.newsviewsv2.api.NewsApiInterface;
import com.greendreamlimited.newsviewsv2.api.RetrofitClient;
import com.greendreamlimited.newsviewsv2.interfaces.OnItemClickListener;
import com.greendreamlimited.newsviewsv2.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String BASE_URL_NEWS = "https://newsapi.org/v2/";
    public static final String API_KEY = "6cc1938921a24360a9f10b2b2eccf4ef";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articleList = new ArrayList<>();
    private NewsViewAdapter newsViewAdapter;
    private String TAG = HomeActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initialization(view);
        return view;
    }

    private void initialization(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.rv_news_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");
    }

    private int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    public void LoadNews(final String keyword) {
        swipeRefreshLayout.setRefreshing(true);
        final NewsApiInterface apiInterface = RetrofitClient.getNewsClient(BASE_URL_NEWS).create(NewsApiInterface.class);
        String country = Utils.getCountry();
        Call<NewsSource> call = apiInterface.getNews(country, API_KEY);
        call.enqueue(new Callback<NewsSource>() {
            @Override
            public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    if (!articleList.isEmpty()) {
                        articleList.clear();
                    }
                    articleList = response.body().getArticles();
                    newsViewAdapter = new NewsViewAdapter(getActivity(), articleList);
                    recyclerView.setAdapter(newsViewAdapter);
                    newsViewAdapter.notifyDataSetChanged();
                    initListener();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsSource> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initListener() {
        newsViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Do you want to use phone browser")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendUrlToGetDetailsNews(position);
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loadBrowser(position);
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    private void loadBrowser(int position) {
        Article article = articleList.get(position);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
        startActivity(intent);
    }

    private void sendUrlToGetDetailsNews(int position) {
        Intent intent = new Intent(getActivity(), DetailsNewsActivity.class);
        Article article = articleList.get(position);
        intent.putExtra("url", article.getUrl());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        LoadNews("");
    }

    private void onLoadingSwipeRefresh(final String keyword) {
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadNews(keyword);
                    }
                }
        );
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

}
