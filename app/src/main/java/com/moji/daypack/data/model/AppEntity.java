package com.moji.daypack.data.model;

import com.moji.daypack.network.ServerConfig;
import com.moji.daypack.util.DateUtils;

/**
 * Created by GuDong on 2016/8/26 16:15.
 * Contact with ruibin.mao@moji.com.
 */
public class AppEntity extends AppLite {
    public String appType;
    public long appFileSize;
    public String appVersionNo;
    public String appIcon;
    public String appDescription;
    public String appScreenshots;

    @Override
    public String getAppIcon() {
        return ServerConfig.ICON_HOST+appIcon;
    }

    @Override
    public String getAppType() {
        return appType;
    }

    @Override
    public long getAppFileSize() {
        return appFileSize;
    }

    @Override
    public String getAppCreated() {
        return DateUtils.formatTimePersonality(appCreated);
    }
}
