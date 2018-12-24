package com.greendreamlimited.newsviewsv2.api;

import com.greendreamlimited.newsviewsv2.Models.NewsSourceApiModel.NewsSource;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("v2/sources?apiKey=6cc1938921a24360a9f10b2b2eccf4ef")
    Call<NewsSource> getNews();
}
