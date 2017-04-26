package com.fkulic.bugsy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetRSSData.OnDataAvailable, ArticleAdapter.OpenArticleInBrowser, AdapterView.OnItemSelectedListener {

    public static final String CATEGORY_ALL = "Sve";

    private SwipeRefreshLayout swipeRefreshArticles;
    private RecyclerView rvArticles;
    private RecyclerView.LayoutManager mManager;
    private ArticleAdapter mArticleAdapter;
    private Spinner sSelectCategory;

    private List<String> mCategories;
    private ArrayAdapter<String> mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mCategories = new ArrayList<>();
        setUpUI();
    }

    private void setUpUI() {
        this.sSelectCategory = (Spinner) findViewById(R.id.sSelectCategory);
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
        String selectedCategory = this.mArticleAdapter.getSelectedCategory();
        this.mArticleAdapter.loadArticles(articles);
        this.swipeRefreshArticles.setRefreshing(false);
        setUpCategoryAdapter(articles);

        // select previously selected category
        if (!this.mCategories.contains(selectedCategory)) {
            selectedCategory = CATEGORY_ALL;
        }
        this.sSelectCategory.setSelection(this.mCategories.indexOf(selectedCategory), false);
        this.mArticleAdapter.setSelectedCategory(selectedCategory);
        this.mArticleAdapter.filterList();
    }

    private void setUpCategoryAdapter(List<Article> articles) {
        this.mCategories = new ArrayList<>();
        for (Article article : articles) {
            String category = article.getCategory();
            if (!this.mCategories.contains(category)) {
                this.mCategories.add(category);
            }
        }
        Collections.sort(this.mCategories);
        this.mCategories.add(0, CATEGORY_ALL);
        this.mCategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.mCategories);
        this.mCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sSelectCategory.setAdapter(this.mCategoryAdapter);
        this.sSelectCategory.setSelection(0, false);
        this.sSelectCategory.setOnItemSelectedListener(this);
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
        this.mArticleAdapter.clear();
        GetRSSData rssData = new GetRSSData(this);
        rssData.getArticleData();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.mArticleAdapter.setSelectedCategory(this.mCategoryAdapter.getItem(position));
        this.mArticleAdapter.filterList();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
