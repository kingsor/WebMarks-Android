package com.neetpiq.android.webmarks;

import android.app.Application;
import android.content.Context;
import android.webkit.WebView;

import com.neetpiq.android.webmarks.utils.PackageUtils;

/**
 * Created by edoardo on 23/08/2015.
 */
public class WebmarksApp extends Application {

    private static Context mContext;
    public static String versionName;

    public static Context getContext() {

        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        versionName = PackageUtils.getVersionName(this);
    }

    /**
     * Device's default User-Agent string.
     * E.g.:
     *    "Mozilla/5.0 (Linux; Android 6.0; Android SDK built for x86_64 Build/MASTER; wv)
     *    AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile
     *    Safari/537.36"
     */
    private static String mDefaultUserAgent;
    public static String getDefaultUserAgent() {
        if (mDefaultUserAgent == null) {
            // TODO: use WebSettings.getDefaultUserAgent() after upgrade to API level 17+
            mDefaultUserAgent = new WebView(getContext()).getSettings().getUserAgentString();
        }
        return mDefaultUserAgent;
    }

    /**
     * From Wordpress-Android
     * User-Agent string when making HTTP connections, for both API traffic and WebViews.
     * Appends "wp-android/version" to WebView's default User-Agent string for the webservers
     * to get the full feature list of the browser and serve content accordingly, e.g.:
     *    "Mozilla/5.0 (Linux; Android 6.0; Android SDK built for x86_64 Build/MASTER; wv)
     *    AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile
     *    Safari/537.36 wp-android/4.7"
     * Note that app versions prior to 2.7 simply used "wp-android" as the user agent
     **/
    private static final String USER_AGENT_APPNAME = "Webmarks-Android";
    private static String mUserAgent;
    public static String getUserAgent() {
        if (mUserAgent == null) {
            mUserAgent = getDefaultUserAgent() + " "
                    + USER_AGENT_APPNAME + "/" + PackageUtils.getVersionName(getContext());
        }
        return mUserAgent;
    }
}
