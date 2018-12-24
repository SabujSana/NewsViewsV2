package com.greendreamlimited.newsviewsv2.api;

import com.greendreamlimited.newsviewsv2.Models.NewsSource.NewsSource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {
    @GET("top-headlines")
    Call<NewsSource> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
