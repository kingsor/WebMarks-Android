package com.neetpiq.android.webmarks.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by edoardo on 18/08/2015.
 */
public class Webmark implements Parcelable {

    private long mId;
    private Date mInsertDate;
    private String mUrl;
    private String mTitle;
    private String mDescription;
    private String mMetadata;

    public Webmark() {
        init("");
    }

    public Webmark(String url) {
        init(url);
    }

    private void init(String url) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeValue(this.mInsertDate);
        dest.writeString(this.mUrl);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mMetadata);
    }

    private Webmark(Parcel in) {
        this.mId = in.readLong();
        this.mInsertDate = (Date) in.readValue(Date.class.getClassLoader());
        this.mUrl = in.readString();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mMetadata = in.readString();
    }

    public static final Parcelable.Creator<Webmark> CREATOR = new Parcelable.Creator<Webmark>() {
        public Webmark createFromParcel(Parcel source) {
            return new Webmark(source);
        }

        public Webmark[] newArray(int size) {
            return new Webmark[size];
        }
    };
}
