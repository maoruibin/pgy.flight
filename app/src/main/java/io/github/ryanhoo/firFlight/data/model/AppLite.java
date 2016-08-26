package io.github.ryanhoo.firFlight.data.model;

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
        return null;
    }

    @Override
    public String getAppName() {
        return null;
    }

    @Override
    public String getAppVersion() {
        return null;
    }

    @Override
    public String getAppBuildVersion() {
        return null;
    }

    @Override
    public String getAppIdentifier() {
        return null;
    }

    @Override
    public String getAppUpdateDescription() {
        return null;
    }
}
