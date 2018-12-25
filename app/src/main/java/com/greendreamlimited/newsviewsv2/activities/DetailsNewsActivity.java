package com.greendreamlimited.newsviewsv2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.greendreamlimited.newsviewsv2.R;

import java.net.URL;

public class DetailsNewsActivity extends AppCompatActivity {
    private ProgressBar pbNewsDetails;
    private WebView wvNewsDetails;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);

        pbNewsDetails = findViewById(R.id.pb_news_details);
        wvNewsDetails = findViewById(R.id.wv_news_details);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        initWebView(url);
    }

    private void initWebView(String url) {
        wvNewsDetails.getSettings().setLoadsImagesAutomatically(true);
        wvNewsDetails.getSettings().setJavaScriptEnabled(true);
        wvNewsDetails.getSettings().setDomStorageEnabled(true);
        wvNewsDetails.getSettings().setSupportZoom(true);
        wvNewsDetails.getSettings().setBuiltInZoomControls(true);
        wvNewsDetails.getSettings().setDisplayZoomControls(false);
        wvNewsDetails.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvNewsDetails.setWebViewClient(new WebViewClient());
        wvNewsDetails.loadUrl(url);
    }
}
