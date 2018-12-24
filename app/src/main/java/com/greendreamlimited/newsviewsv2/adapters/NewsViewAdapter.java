package com.greendreamlimited.newsviewsv2.adapters;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.greendreamlimited.newsviewsv2.Models.NewsSource.Article;
import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.interfaces.ItemClickListener;
import com.greendreamlimited.newsviewsv2.utils.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder> {
    private Context context;
    private List<Article> articleList;

    public NewsViewAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holders, int position) {
        final NewsViewHolder holder = holders;
        Article article = articleList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);
        holder.tvAuthor.setText(article.getAuthor());
        holder.tvTitle.setText(article.getTitle());
        holder.tvDesc.setText(article.getDescription());
        holder.tvPublish.setText(Utils.DateFormat(article.getPublishedAt()));
        holder.tvSource.setText(article.getSource().getName());
        //holder.tvTime.setText(" \u2022 " + Utils.DateToTimeFormat(article.getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemClickListener itemClickListener;
        ImageView imageView;
        TextView tvTitle, tvDesc, tvAuthor, tvPublish, tvSource;
        ProgressBar progressBar;

        public NewsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvAuthor = itemView.findViewById(R.id.tv_news_author);
            tvPublish = itemView.findViewById(R.id.tv_publish_date);
            tvSource = itemView.findViewById(R.id.tv_source);
           // tvTime = itemView.findViewById(R.id.tv_time);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
