package com.fkulic.bugsy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetRSSData.OnDataAvailable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRSSData rssData = new GetRSSData(this);
        rssData.getArticleData();
    }

    @Override
    public void onDataAvailable(List<Article> articles) {
        for (Article article : articles) {
            Log.d("Article", "title: " + article.getTitle());
            Log.d("Article", "category: " + article.getCategory());
            Log.d("Article", "image " + article.getImgUrl());
        }
    }

    @Override
    public void onDataNotAvailable() {
        Toast.makeText(this, "Couldn't get data.", Toast.LENGTH_SHORT).show();
    }
}
