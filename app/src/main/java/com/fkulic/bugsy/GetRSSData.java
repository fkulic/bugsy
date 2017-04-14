package com.fkulic.bugsy;

import android.util.Log;

import com.fkulic.bugsy.rsshelper.RSSResult;
import com.fkulic.bugsy.rsshelper.RSSRoot;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Filip on 14.4.2017..
 */

public class GetRSSData implements Callback<RSSRoot> {
    private static final String TAG = "GetRSSData";

    public static final String BUG_RSS_BASE_URL = "http://www.bug.hr/rss/vijesti/";
    private final OnDataAvailable mDataAvailable;


    public interface OnDataAvailable {
        void onDataAvailable(List<Article> articles);
        void onDataNotAvailable();
    }

    public GetRSSData(OnDataAvailable dataAvailable) {
        mDataAvailable = dataAvailable;
    }

    public void getArticleData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BUG_RSS_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        RSSResult rss = retrofit.create(RSSResult.class);
        Call<RSSRoot> call = rss.getArticles();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RSSRoot> call, Response<RSSRoot> response) {
        List<Article> articles = response.body().getArticles();
        mDataAvailable.onDataAvailable(articles);
    }

    @Override
    public void onFailure(Call<RSSRoot> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
        mDataAvailable.onDataNotAvailable();
    }
}
