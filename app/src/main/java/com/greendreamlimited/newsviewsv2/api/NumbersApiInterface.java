package com.greendreamlimited.newsviewsv2.api;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NumbersApiInterface {

    @GET("/{number}")
    Call<ResponseBody> getNumberInfo(@Path("number") String number);

    @GET("/{date}/date")
    Call<ResponseBody> getDateInfo(@Path("date") String date);
}
