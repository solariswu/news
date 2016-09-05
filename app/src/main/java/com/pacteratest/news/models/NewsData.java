
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
    private List<Row> rows = new ArrayList<>();

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
    public List<Row> getRows() {
        return rows;
    }

    /**
     * 
     * @param rows
     *     The rows
     */
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

}
