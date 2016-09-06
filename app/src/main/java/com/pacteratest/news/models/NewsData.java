
package com.pacteratest.news.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private List<NewsRowData> newsRowDatas = new ArrayList<>();

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The rows
     */
    public List<NewsRowData> getRows() {
        return newsRowDatas;
    }

    /**
     * 
     * @param newsRowDatas
     *     The newsRowDatas
     */
    public void setRows(List<NewsRowData> newsRowDatas) {
        this.newsRowDatas = newsRowDatas;
    }

}
