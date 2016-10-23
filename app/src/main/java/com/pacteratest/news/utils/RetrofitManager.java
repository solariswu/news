package com.pacteratest.news.utils;

import android.util.Log;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Yungang Wu on 16/9/5.
 *
 */
public class RetrofitManager {
    private Retrofit mNewsRetrofit;
    private OkHttpClient httpClient;
    private static RetrofitManager uniqueInstance = new RetrofitManager();
    public static RetrofitManager getInstance() {
        return uniqueInstance;
    }

    private RetrofitManager() {
        if (null == httpClient) {
            // setting log level
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // add logging as last interceptor
            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Log.i(NewsConstants.LOG, "request :" + request.url().toString());
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging)
                    .build();
        }
    }

    public Retrofit buildNewsRetrofit(String SERVICE_API_BASE_URL) {
        if (null == mNewsRetrofit) {
            mNewsRetrofit = new Retrofit.Builder()
                    .baseUrl(SERVICE_API_BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mNewsRetrofit;
    }
}