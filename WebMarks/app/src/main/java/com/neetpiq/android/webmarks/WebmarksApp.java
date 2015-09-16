package com.neetpiq.android.webmarks;

import android.app.Application;
import android.content.Context;

/**
 * Created by edoardo on 23/08/2015.
 */
public class WebmarksApp extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }
}
