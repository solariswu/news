
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
    private String imageHref;

    public NewsRowData (String title, String description, String imageHref) {
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
        if (null != title) {
            return title.trim();
        }
        return null;
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
        if (null != description) {
            return description.trim();
        }
        return null;
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
    public String getImageHref() { return imageHref; }

    /**
     * 
     * @param imageHref
     *     The imageHref
     */
    public void setImageHref(String imageHref) { this.imageHref = imageHref; }

}
