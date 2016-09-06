package com.pacteratest.news.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.pacteratest.news.R;
import com.pacteratest.news.adapters.NewsItemAdapter;
import com.pacteratest.news.models.NewsData;
import com.pacteratest.news.models.NewsRowData;
import com.pacteratest.news.services.RetrofitService;
import com.pacteratest.news.utils.NewsConstants;
import com.pacteratest.news.utils.RetrofitManager;

import java.util.Iterator;
import java.util.List;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private NewsData mNewsData;
    private final static String ACTION_NEWS_DATA_NOTIFY = "ACTION_NEWS_DATA_NOTIFY";
    private BroadcastReceiver mReceiver;
    private SwipeRefreshLayout mSwipeLayout;
    private List<NewsRowData> mList;
    private NewsItemAdapter mNewsItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //set refresh listener
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        if (null!= mSwipeLayout) {
            mSwipeLayout.setOnRefreshListener(this);
        }

        // create receiver to get news data update
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String intentAction = intent.getAction();

                if (intentAction.equals(NewsActivity.ACTION_NEWS_DATA_NOTIFY) &&
                        null != mNewsData) {

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println(Thread.currentThread().getId());
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    updateNewsUI(mNewsData);
                                }
                            });
                        }

                    }).start();
                }
            }
        };

        registerReceiver(mReceiver, new IntentFilter(NewsActivity.ACTION_NEWS_DATA_NOTIFY));

        new onRequestNewsData().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new onRequestNewsData().execute();
            }
        }, 500);

        /*
        不用延迟可以直接像下面这样写
         */
        /*
        *  list.clear();
                 addItems();
                 adapter.notifyDataSetChanged();
                 swipeContainer.setRefreshing(false);
                 */
    }


    // remove the items in list whose datas are all null
    private static List<NewsRowData> removeAllNullNewsData (List<NewsRowData> newsRowDatas) {

        if (newsRowDatas == null) {
            return null;
        }
        Iterator<?> iterator = newsRowDatas.iterator();
        while (iterator.hasNext()) {
            NewsRowData newsRowData = (NewsRowData) iterator.next();
            if (null == newsRowData.getDescription() &&
                    null == newsRowData.getImageHref() &&
                    null == newsRowData.getTitle()) {
                iterator.remove();
            }
        }
        return newsRowDatas;
    }

    private void updateNewsUI(NewsData newsData) {
        //set action bar title
        setTitle(newsData.getTitle());

        // set daily view list
        ListView lvNews = (ListView) findViewById(R.id.lvNewsList);
        if (null != lvNews) {
            mList = newsData.getRows();
            //remove all null data items
            removeAllNullNewsData(mList);

            mNewsItemAdapter = new NewsItemAdapter(this, mList, lvNews);
            //set Adapater
            lvNews.setAdapter(mNewsItemAdapter);

        }
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
            mSwipeLayout.setRefreshing(false);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

}

