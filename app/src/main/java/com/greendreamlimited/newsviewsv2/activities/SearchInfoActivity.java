package com.greendreamlimited.newsviewsv2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.api.NewsApiInterface;
import com.greendreamlimited.newsviewsv2.api.NumbersApiInterface;
import com.greendreamlimited.newsviewsv2.api.RetrofitClient;
import com.greendreamlimited.newsviewsv2.api.RetrofitNumbersClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchInfoActivity extends AppCompatActivity {
    TextView tvSearchInfo;
    private static final String BASE_URL_NUMBERS = "https://numbersapi.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        tvSearchInfo = findViewById(R.id.tv_search_info);
        String queryText = getIntent().getStringExtra("query");
        if (queryText.contains("/")) {
            loadDateData(queryText);
        } else {
            loadNumberData(queryText);
        }
    }

    private void loadNumberData(String queryText) {
        NumbersApiInterface apiInterface = RetrofitNumbersClient.getNumbersClient(BASE_URL_NUMBERS).create(NumbersApiInterface.class);
        Call<String> call = apiInterface.getNumberInfo(queryText);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tvSearchInfo.setText(response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void loadDateData(String queryText) {
        NumbersApiInterface apiInterface = RetrofitNumbersClient.getNumbersClient(BASE_URL_NUMBERS).create(NumbersApiInterface.class);
        Call<String> call = apiInterface.getDateInfo(queryText);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tvSearchInfo.setText(response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
