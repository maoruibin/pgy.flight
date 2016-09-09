package com.moji.daypack.data.model;

/**
 * 最小 App 实体
 * Created by GuDong on 2016/8/26 16:14.
 * Contact with ruibin.mao@moji.com.
 */
public class AppLite implements IAppBasic{
    public String appKey;
    public String userKey;
    public String appName;
    public String appVersion;
    public String appBuildVersion;
    public String appIdentifier;
    public String appCreated;
    public String appUpdateDescription;

    @Override
    public String getAppIcon() {
        return null;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public String getAppVersion() {
        return appVersion;
    }

    @Override
    public String getAppBuildVersion() {
        return appBuildVersion;
    }

    @Override
    public String getAppIdentifier() {
        return appIdentifier;
    }

    @Override
    public String getAppUpdateDescription() {
        return appUpdateDescription;
    }

    @Override
    public String getAppType() {
        return 2+"";
    }

    @Override
    public String getAppCreated() {
        return appCreated;
    }

    @Override
    public long getAppFileSize() {
        return 0;
    }

    @Override
    public boolean isAppLatest() {
        return false;
    }

    @Override
    public String getAppVersionNo() {
        return null;
    }
}
