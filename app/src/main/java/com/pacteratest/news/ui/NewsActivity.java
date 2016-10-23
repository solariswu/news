package com.pacteratest.news.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.pacteratest.news.R;
import com.pacteratest.news.adapters.NewsItemAdapter;
import com.pacteratest.news.models.NewsData;
import com.pacteratest.news.models.NewsRowData;

import java.util.Iterator;
import java.util.List;


public class NewsActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        NewsView {

    private SwipeRefreshLayout mSwipeLayout;

    private NewsPresenter   mNewsPresenter;

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
        // Bind presenter
        mNewsPresenter = new NewsPresenter();
        mNewsPresenter.onCreate(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNewsPresenter.refresh();
            }
        }, 500);

    }

    @Override
    public void initViews() {

    }

    // remove the items in list whose data is null
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

    public void updateNewsUI(NewsData newsData) {
        //set action bar title
        setTitle(newsData.getTitle());

        // set daily view list
        ListView lvNews = (ListView) findViewById(R.id.lvNewsList);
        if (null != lvNews) {
            List<NewsRowData> list = newsData.getRows();
            //remove all null data items
            removeAllNullNewsData(list);
            //set Adapater
            lvNews.setAdapter(new NewsItemAdapter(this, list, lvNews));
        }
    }



}

