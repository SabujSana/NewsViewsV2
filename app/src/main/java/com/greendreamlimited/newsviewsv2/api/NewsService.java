package com.greendreamlimited.newsviewsv2.api;

public class NewsService {
    private static final String BASE_URL="https://newsapi.org/";
    public static final String API_KEY="6cc1938921a24360a9f10b2b2eccf4ef";

    public static NewsApi getNewsService(){
        return RetrofitClient.getClient(BASE_URL).create(NewsApi.class);
    }
}
