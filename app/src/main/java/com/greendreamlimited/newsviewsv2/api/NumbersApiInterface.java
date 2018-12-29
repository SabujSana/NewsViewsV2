package com.greendreamlimited.newsviewsv2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NumbersApiInterface {

    @GET("{number}")
    Call<String> getNumberInfo(@Path("number") String number);

    @GET("{date}/date")
    Call<String> getDateInfo(@Path("date") String date);
}
