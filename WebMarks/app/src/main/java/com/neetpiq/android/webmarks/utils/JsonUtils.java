package com.neetpiq.android.webmarks.utils;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by edoardo on 29/08/2015.
 */
public class JsonUtils {

    private static final String TAG = "JsonUtils";

    private static final String JSON_NULL_STR = "null";

    /*
     * wrapper for JSONObject.optString() which handles "null" values
     */
    public static String getString(JSONObject json, String name) {
        String value = json.optString(name);
        // return empty string for "null"
        if (JSON_NULL_STR.equals(value))
            return "";
        return value;
    }

    /*
     * use with strings that contain HTML entities
     */
    /*public static String getStringDecoded(JSONObject json, String name) {
        String value = getString(json, name);
        return HtmlUtils.fastUnescapeHtml(value);
    }*/

    /*
     * replacement for JSONObject.optBoolean()  - optBoolean() only checks for "true" and "false",
     * but our API sometimes uses "0" to denote false
     */
    public static boolean getBool(JSONObject json, String name) {
        String value = getString(json, name);
        if (TextUtils.isEmpty(value))
            return false;
        if (value.equals("0"))
            return false;
        if (value.equalsIgnoreCase("false"))
            return false;
        return true;
    }
}
