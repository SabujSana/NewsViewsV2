package com.greendreamlimited.newsviewsv2.activities;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.greendreamlimited.newsviewsv2.Models.NewsSource.Article;
import com.greendreamlimited.newsviewsv2.Models.NewsSource.NewsSource;
import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.adapters.NewsViewAdapter;
import com.greendreamlimited.newsviewsv2.api.NewsApiInterface;
import com.greendreamlimited.newsviewsv2.api.RetrofitClient;
import com.greendreamlimited.newsviewsv2.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    public static final String API_KEY = "6cc1938921a24360a9f10b2b2eccf4ef";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articleList = new ArrayList<>();
    private NewsViewAdapter newsViewAdapter;
    private String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.rv_news_list);
        layoutManager = new GridLayoutManager(HomeActivity.this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadNews();

    }

    private int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    public void LoadNews() {
        final NewsApiInterface apiInterface = RetrofitClient.getClient().create(NewsApiInterface.class);
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
                    newsViewAdapter = new NewsViewAdapter(HomeActivity.this, articleList);
                    recyclerView.setAdapter(newsViewAdapter);
                    newsViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomeActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsSource> call, Throwable t) {

            }
        });

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
