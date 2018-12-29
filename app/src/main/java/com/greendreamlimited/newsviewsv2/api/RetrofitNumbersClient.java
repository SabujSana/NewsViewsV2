package com.greendreamlimited.newsviewsv2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNumbersClient {
    private static Retrofit retrofitNumbers = null;
    public static Retrofit getNumbersClient(String baseUrl) {
        if (retrofitNumbers == null) {
            retrofitNumbers = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitNumbers;
    }
}
