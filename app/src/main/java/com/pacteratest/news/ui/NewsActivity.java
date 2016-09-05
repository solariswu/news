package com.pacteratest.news.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pacteratest.news.R;
import com.pacteratest.news.models.NewsData;
import com.pacteratest.news.services.RetrofitService;
import com.pacteratest.news.utils.NewsConstants;
import com.pacteratest.news.utils.RetrofitManager;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class NewsActivity extends AppCompatActivity {
    private NewsData mNewsData;
    private final String ACTION_NEWS_DATA_NOTIFY = "news data";
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // create receiver to get weather data update
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String intentAction = intent.getAction();

                if (intentAction.equals(NewsActivity.ACTION_NEWS_DATA_NOTIFY) &&
                        null != mNewsData) {
                    updateNewsUI(mNewsData);
                }
            }
        };

        registerReceiver(mReceiver, new IntentFilter(NewsActivity.ACTION_NEWS_DATA_NOTIFY));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void updateNewsUI(NewsData newsData) {
    }

    private class onRequestNewsData extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... params) {

            try {
                Retrofit retrofit = RetrofitManager.getInstance().buildWeatherRetrofit(
                        NewsConstants.NEWS_DATA_URL);
                RetrofitService retrofitService = retrofit.create(RetrofitService.class);

                // Call Retrofit service to fetch News data from server URL
                Call<NewsData> call = retrofitService.getNewsData(NewsConstants.APIKEY,
                        NewsConstants.NEWSDATA_JSON_FILE_NAME);
                Response<NewsData> responseResponse;
                responseResponse = call.execute();
                if (responseResponse.isSuccess()) {
                    mNewsData = responseResponse.body();
                    Intent data = new Intent(ACTION_NEWS_DATA_NOTIFY);
                    sendBroadcast(data);
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }

            return 0L;
        }

        @Override
        protected void onPostExecute(Long result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

}

