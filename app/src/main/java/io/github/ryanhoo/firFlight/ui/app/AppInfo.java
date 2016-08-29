package io.github.ryanhoo.firFlight.ui.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import io.github.ryanhoo.firFlight.data.model.IAppBasic;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/20/16
 * Time: 4:19 PM
 * Desc: AppInfo
 */
public class AppInfo {

    private static final String TAG = "AppInfo";

    public boolean isInstalled;
    public boolean isUpToDate;

    public String localVersionName;
    public int localVersionCode;

    public Intent launchIntent;

    public IAppBasic app;

    public AppInfo(Context context, IAppBasic app) {
        this.app = app;
        String packageName = app.getAppIdentifier();
        String build = app.getAppBuildVersion();
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            if (packageInfo == null) {
                isInstalled = false;
                isUpToDate = false;
            } else {
                isInstalled = true;
                int onlineVersionCode = Integer.parseInt(build);
                localVersionName = packageInfo.versionName;
                localVersionCode = packageInfo.versionCode;
                isUpToDate = localVersionCode >= onlineVersionCode;
                if (isUpToDate) {
                    // http://stackoverflow.com/a/3422824/2290191
                    launchIntent = packageManager.getLaunchIntentForPackage(packageName);
                }
            }
        } catch (PackageManager.NameNotFoundException ignore) {
            // It means app is not installed, no need to throw or log out errors
        } catch (Exception e) {
            Log.w(TAG, String.format("isAppUpToDate %s: [%s, %s]", app.getAppName(), packageName, build), e);
        }
    }
}
