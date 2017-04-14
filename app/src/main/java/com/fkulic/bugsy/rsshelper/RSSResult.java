package com.fkulic.bugsy.rsshelper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Filip on 14.4.2017..
 */

public interface RSSResult {
    @GET(".")
    Call<RSSRoot> getArticles();
}
