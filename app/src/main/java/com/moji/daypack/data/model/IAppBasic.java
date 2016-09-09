package com.moji.daypack.data.model;

/**
 * Created by GuDong on 2016/8/26 16:25.
 * Contact with ruibin.mao@moji.com.
 */
public interface IAppBasic {

    String getAppIcon();

    String getAppKey();

    String getAppName();

    String getAppVersion();

    String getAppBuildVersion();

    /**
     * Android 的 version code
     * @return
     */
    String getAppVersionNo();

    String getAppIdentifier();

    String getAppUpdateDescription();

    String getAppType();

    String getAppCreated();

    long getAppFileSize();

    boolean isAppLatest();

}
