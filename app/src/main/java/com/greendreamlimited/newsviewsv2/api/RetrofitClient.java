package com.greendreamlimited.newsviewsv2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitNews = null;

    public static Retrofit getNewsClient(String baseUrl) {
        if (retrofitNews == null) {
            retrofitNews = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitNews;
    }

}
