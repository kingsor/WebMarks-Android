package com.neetpiq.android.webmarks.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.neetpiq.android.webmarks.BuildConfig;

/**
 * Created by Edoardo on 05-Dec-15.
 * from "package org.wordpress.android.util"
 */
public class PackageUtils {
    /**
     * Return true if Debug build. false otherwise.
     */
    public static boolean isDebugBuild() {
        return BuildConfig.DEBUG;
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            return manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * Return version code, or 0 if it can't be read
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    /**
     * Return version name, or the string "0" if it can't be read
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return "0";
    }
}
