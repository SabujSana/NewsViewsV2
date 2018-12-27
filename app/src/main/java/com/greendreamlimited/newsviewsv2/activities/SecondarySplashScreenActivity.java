package com.greendreamlimited.newsviewsv2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.utils.SharedPreferenceManager;

public class SecondarySplashScreenActivity extends AppCompatActivity {
    private SharedPreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_splash_screen);

        if (preferenceManager.isFirstTimeLaunch()) {
          startActivity(new Intent(SecondarySplashScreenActivity.this,SplashScreenActivity.class));
          finish();
        }
    }
}
