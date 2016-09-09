package com.moji.daypack.data.model;

import com.moji.daypack.network.ServerConfig;
import com.moji.daypack.util.DateUtils;

import java.util.List;

/**
 * Created by GuDong on 8/28/16 21:44.
 * Contact with gudong.name@gmail.com.
 */
public class AppDetailModel implements IAppBasic{
    public String appKey;
    public String userKey;
    public String appType;
    public String appIsLastest;
    public long appFileSize;
    public String appName;
    public String appVersion;
    public String appVersionNo;
    public String appBuildVersion;
    public String appIdentifier;
    public String appIcon;
    public String appDescription;
    public String appUpdateDescription;
    public String appScreenshots;
    public String appShortcutUrl;
    public String appCreated;
    public String appUpdated;
    public String appQRCodeURL;
    public String appFollowed;
    public String otherAppsCount;
    public String commentsCount;
    /**
     * appKey : a8fe6fa3ea26af7930a7fb95212f6f75
     * userKey : 3172bed7694c12e7336ca602d0c158bb
     * appName : Android 6.0
     * appVersion : 6.0002.02
     * appBuildVersion : 35
     * appIdentifier : com.moji.mjweather
     * appCreated : 2天前
     * appUpdateDescription : 1、增加读取5.x 版本UID的方法 @段迪
     2、修改ZoomOutPageTransformer @张徐峰
     3、增加log日志，定位Widget部分图片绘制不出 @张徐峰
     4、修改皮肤小铺请求参数的名称 @张徐峰
     5、修改SkinShop加载图片的View @张徐峰
     6、修改皮肤小铺Constant名字 @张徐峰
     【已知问题】
     无
     */

    public List<AppLite> otherapps;
    public List<?> comments;

    @Override
    public String getAppIcon() {
        return ServerConfig.ICON_HOST+appIcon;
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
    public String getAppVersionNo() {
        return appVersionNo;
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
        return appType;
    }

    @Override
    public String getAppCreated() {
        return DateUtils.formatTimePersonality(appCreated);
    }

    @Override
    public long getAppFileSize() {
        return appFileSize;
    }


    @Override
    public boolean isAppLatest() {
        return "1".equals(appIsLastest);
    }
}
