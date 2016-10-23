package com.pacteratest.news.ui;

import com.pacteratest.news.models.NewsData;

/**
 * Created by solariswu on 16/10/22.
 *
 */

interface NewsView {

    void initViews ();

    void updateNewsUI (NewsData newsData);

}
