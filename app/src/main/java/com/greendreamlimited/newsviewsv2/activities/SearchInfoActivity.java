package com.greendreamlimited.newsviewsv2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.greendreamlimited.newsviewsv2.R;

public class SearchInfoActivity extends AppCompatActivity {
    TextView tvSearchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        String queryText = getIntent().getStringExtra("query");
        if (queryText.contains("/")) {
            loadDateData(queryText);
        } else {
            loadNumberData(queryText);
        }
    }

    private void loadNumberData(String queryText) {
        Toast.makeText(this, queryText, Toast.LENGTH_SHORT).show();
    }

    private void loadDateData(String queryText) {
        Toast.makeText(this, queryText, Toast.LENGTH_SHORT).show();
    }
}
