package com.pacteratest.news.services;


import com.pacteratest.news.models.NewsData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by solariswu on 05/9/16.
 *
 */

/**
 * Provides the interface for {@link retrofit2.Retrofit} describing the endpoints and responses
 * for the endpoints.
 */

public interface RetrofitService {

    @GET("/u/{APIKEY}/{JSONFILE}")
    Observable<NewsData> getNewsData(@Path("APIKEY") String apikey, @Path("JSONFILE") String jsonFile);
}
