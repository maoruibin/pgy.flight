package com.moji.daypack.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.moji.daypack.data.model.App;
import com.moji.daypack.data.model.Release;
import com.moji.daypack.network.ServerConfig;

import java.io.File;

import com.moji.daypack.data.model.IAppBasic;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/20/16
 * Time: 4:01 PM
 * Desc: AppUtils
 */
public class AppUtils {
    private static File DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    public static final int UNINSTALL_REQUEST_CODE = 1;

    private static final String TAG = "AppUtils";

    public static boolean isAppInstalled(Context context, IAppBasic app) {
        String packageName = app.getAppIdentifier();
        return isAppInstalled(context,packageName);
    }

    public static boolean isAppInstalled(Context context,String packageName){
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_META_DATA);
            return packageInfo != null;
        } catch (Exception e) {
            Log.e(TAG, "isAppInstalled: " + packageName, e);
        }
        return false;
    }

    public static boolean isAppUpToDate(Context context, App app) {
        Release onlineRelease = app.getMasterRelease();
        String packageName = app.getBundleId();
        String build = onlineRelease.getBuild();
        try {
            int onlineVersionCode = Integer.parseInt(build);
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_META_DATA);
            int versionCode = packageInfo.versionCode;
            return versionCode >= onlineVersionCode;
        } catch (Exception e) {
            Log.e(TAG, String.format("isAppUpToDate %s: [%s, %s]", app.getName(), packageName, build), e);
        }
        return false;
    }

    public static String getFlavorName(Context context) {
        if (context == null) return null;
        String flavorName = null;
        try {
            Context applicationContext = context.getApplicationContext();
            ApplicationInfo appInfo = applicationContext.getPackageManager()
                    .getApplicationInfo(applicationContext.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                flavorName = metaData.getString("FLIGHT_FLAVOR_NAME");
                Log.d(TAG, "getFlavorName: " + flavorName);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getFlavorName failed: ", e);
        }
        return flavorName;
    }

    public static String getAppUrlByShort(String shortUrl) {
        return String.format("%s/%s", ServerConfig.FIR_HOST, shortUrl);
    }

    public static void uninstallApp(Activity activity, String packageName) {
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        intent.setData(Uri.parse("package:" + packageName));
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        activity.startActivityForResult(intent, UNINSTALL_REQUEST_CODE);
    }

    public static boolean isAndroidApp(String type) {
        return 2 == Integer.parseInt(type);
    }

    public static boolean isIosApp(String type) {
        return 1 == Integer.parseInt(type);
    }

    public static String formatApkName(IAppBasic app){
        return app.getAppName()+"_"+app.getAppKey().concat(".apk");
    }

    public static File getInstalledApkFile(IAppBasic app){
        return new File(DOWNLOAD_DIR,formatApkName(app));
    }
}
