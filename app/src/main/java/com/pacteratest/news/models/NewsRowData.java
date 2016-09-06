
package com.pacteratest.news.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsRowData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageHref")
    @Expose
    private Object imageHref;

    public NewsRowData (String title, String description, Object imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }

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
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The imageHref
     */
    public Object getImageHref() {
        return imageHref;
    }

    /**
     * 
     * @param imageHref
     *     The imageHref
     */
    public void setImageHref(Object imageHref) {
        this.imageHref = imageHref;
    }

}
