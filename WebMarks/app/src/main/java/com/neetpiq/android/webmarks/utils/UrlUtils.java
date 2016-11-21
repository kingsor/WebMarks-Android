package com.neetpiq.android.webmarks.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.net.URI;
import java.net.URL;

/**
 * Created by edoardo on 29/08/2015.
 */
public class UrlUtils {

    public static final String TAG = "UrlUtils";

    /**
     *
     * @param urlString url to get domain from
     * @return domain of uri if available. Empty string otherwise.
     */
    public static String getDomainFromUrl(final String urlString) {
        if (!TextUtils.isEmpty(urlString)) {
            Uri uri = Uri.parse(urlString);
            if (uri.getHost() != null) {
                return uri.getHost();
            }
        }

        return "";
    }


    /**
     * returns true if passed url is https:
     */
    public static boolean isHttps(final String urlString) {
        return (urlString != null && urlString.startsWith("https:"));
    }

    /**
     * returns https: version of passed http: url
     */
    public static String makeHttps(final String urlString) {
        if (urlString == null || !urlString.startsWith("http:")) {
            return urlString;
        }
        return "https:" + urlString.substring(5, urlString.length());
    }

    /**
     * returns false if the url is not valid or if the url host is null, else true
     */
    public static boolean isValidUrlAndHostNotNull(String urlString) {
        try {
            URL url = new URL(urlString);

            if (TextUtils.isEmpty(url.getHost())) {
                Log.d(TAG, "uri.getHost() is empty or null");
                return false;
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error validating url", ex);
            return false;
        }
        return true;
    }
}
