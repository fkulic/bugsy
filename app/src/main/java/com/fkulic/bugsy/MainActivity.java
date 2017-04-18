package com.fkulic.bugsy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetRSSData.OnDataAvailable, ArticleAdapter.OpenArticleInBrowser {

    private SwipeRefreshLayout swipeRefreshArticles;
    private RecyclerView rvArticles;
    private RecyclerView.LayoutManager mManager;
    private ArticleAdapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
    }

    private void setUpUI() {
        this.rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        this.mManager = new LinearLayoutManager(this);
        this.mArticleAdapter = new ArticleAdapter(this);
        this.rvArticles.setLayoutManager(this.mManager);
        this.rvArticles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        this.rvArticles.setAdapter(this.mArticleAdapter);
        this.swipeRefreshArticles = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshArticles);
        swipeRefreshArticles.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onDataAvailable(List<Article> articles) {
        this.mArticleAdapter.loadArticles(articles);
        this.swipeRefreshArticles.setRefreshing(false);
    }

    @Override
    public void onDataNotAvailable() {
        this.swipeRefreshArticles.setRefreshing(false);
        Toast.makeText(this, "Couldn't get data.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openArticleInBrowser(int position) {
        Intent intent = new Intent();
        Uri uri = Uri.parse(mArticleAdapter.mArticles.get(position).getLink());
        intent.setData(uri);
        intent.setAction(Intent.ACTION_VIEW);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "There is no browser installed on your device.", Toast.LENGTH_SHORT).show();
        } 
    }

    private void getData() {
        mArticleAdapter.clear();
        GetRSSData rssData = new GetRSSData(this);
        rssData.getArticleData();
    }
}
