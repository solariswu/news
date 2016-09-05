package com.pacteratest.news.services;


import com.pacteratest.news.models.NewsData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by yungang wu on 05/9/16.
 *
 */

/**
 * Provides the interface for {@link retrofit.Retrofit} describing the endpoints and responses
 * for the endpoints.
 */

public interface RetrofitService {

    @GET("/u/{APIKEY}/{JSONFILE}")
    Call<NewsData> getNewsData(@Path("APIKEY") String apikey, @Path("JSONFILE") String jsonFile);
}
