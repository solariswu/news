package com.pacteratest.news.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pacteratest.news.models.NewsData;
import com.pacteratest.news.services.RetrofitService;
import com.pacteratest.news.utils.NewsConstants;
import com.pacteratest.news.utils.RetrofitManager;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by solariswu on 16/10/22.
 *
 */

public class NewsPresenter {

    //Servcie to get news data
    private RetrofitService mNewsRetrofitService;

    @Nullable
    //Link to the View
    private NewsView mView;

    public void onCreate(@NonNull NewsView newsView) {
        mView = newsView;

        // call View's implemented methods
        mView.initViews();

        // prepare the retrofit services
        Retrofit geoLocationRetrofit = RetrofitManager.getInstance()
                .buildNewsRetrofit(NewsConstants.NEWS_DATA_URL);

        mNewsRetrofitService = geoLocationRetrofit.create(RetrofitService.class);

        requestNewsData();

    }

    private void requestNewsData () {
        mNewsRetrofitService.getNewsData(NewsConstants.APIKEY,
                NewsConstants.NEWSDATA_JSON_FILE_NAME)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsData>() {
                    @Override
                    public void onCompleted() {
                        Log.i (NewsConstants.LOG, "Get Weather Data complete.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e (NewsConstants.LOG, "Get News Data meets error: "+e.getMessage());
                    }

                    @Override
                    public void onNext(NewsData newsData) {
                        if (null != mView) {
                            mView.updateNewsUI(newsData);
                        }
                    }
                });
    }

    public void refresh () {
        requestNewsData();
    }
}