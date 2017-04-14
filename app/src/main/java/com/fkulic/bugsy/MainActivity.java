package com.fkulic.bugsy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetRSSData.OnDataAvailable {

    RecyclerView rvArticles;
    RecyclerView.LayoutManager mManager;

    ArticleAdapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
    }

    private void setUpUI() {
        this.rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        this.mManager = new LinearLayoutManager(this);
        this.mArticleAdapter = new ArticleAdapter(new ArrayList<Article>());
        this.rvArticles.setLayoutManager(this.mManager);
        this.rvArticles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        this.rvArticles.setAdapter(this.mArticleAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRSSData rssData = new GetRSSData(this);
        rssData.getArticleData();
    }

    @Override
    public void onDataAvailable(List<Article> articles) {
        this.mArticleAdapter.loadArticles(articles);
    }

    @Override
    public void onDataNotAvailable() {
        Toast.makeText(this, "Couldn't get data.", Toast.LENGTH_SHORT).show();
    }
}
