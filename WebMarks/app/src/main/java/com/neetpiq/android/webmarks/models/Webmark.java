package com.neetpiq.android.webmarks.models;

import java.util.Date;

/**
 * Created by edoardo on 18/08/2015.
 */
public class Webmark {

    private long mId;
    private Date mInsertDate;
    private String mUrl;
    private String mTitle;
    private String mDescription;
    private String mMetadata;

    public Webmark(String url) {
        mId = -1;
        mInsertDate = new Date();
        mUrl = url;
        mTitle = "";
        mDescription = "";
        mMetadata = "";
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public Date getInsertDate() {
        return mInsertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.mInsertDate = insertDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getMetadata() {
        return mMetadata;
    }

    public void setMetadata(String metadata) {
        this.mMetadata = metadata;
    }
}
