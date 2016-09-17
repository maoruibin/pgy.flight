package com.moji.daypack.data.model;import android.text.TextUtils;import com.moji.daypack.network.ServerConfig;import com.moji.daypack.util.DateUtils;/** * Created by GuDong on 2016/8/26 16:15. * Contact with ruibin.mao@moji.com. */public class AppEntity extends AppLite {    public String appType;    public long appFileSize;    public String appVersionNo;    public String appIcon;    public String appDescription;    public String appScreenshots;    @Override    public String getAppIcon() {        if(appIcon.startsWith("http")){            return appIcon;        }        return ServerConfig.ICON_HOST+appIcon;    }    @Override    public String getAppType() {        return appType;    }    @Override    public long getAppFileSize() {        return appFileSize;    }    @Override    public String getAppCreated() {        return DateUtils.formatTimePersonality(appCreated);    }    @Override    public String getAppBuildVersion() {        // Android version code        if(!TextUtils.isEmpty(appBuildVersion)){            return appBuildVersion;        }        //pgy auto make        return appVersionNo;    }    public void setAppDescription(String appDescription) {        this.appDescription = appDescription;    }    public void setAppFileSize(long appFileSize) {        this.appFileSize = appFileSize;    }    public void setAppIcon(String appIcon) {        this.appIcon = appIcon;    }    public void setAppScreenshots(String appScreenshots) {        this.appScreenshots = appScreenshots;    }    public void setAppType(String appType) {        this.appType = appType;    }    public void setAppVersionNo(String appVersionNo) {        this.appVersionNo = appVersionNo;    }    @Override    public String toString() {        return "AppEntity{" +                super.toString()+" "+                "appDescription='" + appDescription + '\'' +                ", appType='" + appType + '\'' +                ", appFileSize=" + appFileSize +                ", appVersionNo='" + appVersionNo + '\'' +                ", appIcon='" + appIcon + '\'' +                ", appScreenshots='" + appScreenshots + '\'' +                '}';    }}