package com.neetpiq.android.webmarks.utils;

import android.net.Uri;

import java.net.URI;

/**
 * Created by edoardo on 29/08/2015.
 */
public class UrlUtils {

    /**
     *
     * @param urlString url to get domain from
     * @return domain of uri if available. Empty string otherwise.
     */
    public static String getDomainFromUrl(final String urlString) {
        if (urlString != null) {
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
    public static boolean isValidUrlAndHostNotNull(String url) {
        try {
            URI uri = URI.create(url);
            if (uri.getHost() == null) {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
