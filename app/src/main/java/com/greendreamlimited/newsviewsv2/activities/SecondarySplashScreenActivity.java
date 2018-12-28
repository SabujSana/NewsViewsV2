package com.greendreamlimited.newsviewsv2.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.utils.SharedPreferenceManager;
import com.koushikdutta.ion.Ion;

public class SecondarySplashScreenActivity extends AppCompatActivity {
    private SharedPreferenceManager preferenceManager;
    private ImageView ivSplash;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new SharedPreferenceManager(this);
        if (preferenceManager.isFirstTimeLaunch()) {
            startActivity(new Intent(SecondarySplashScreenActivity.this, SplashScreenActivity.class));
            finish();
        }
        setContentView(R.layout.activity_secondary_splash_screen);
        ivSplash = findViewById(R.id.iv_splash_git);
        Ion.with(ivSplash).load("http://supdogs.com/wp-content/themes/resto/assets/images/load.gif");
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SecondarySplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}
