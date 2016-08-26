package io.github.ryanhoo.firFlight.data.model;

/**
 * Created by GuDong on 2016/8/26 16:15.
 * Contact with ruibin.mao@moji.com.
 */
public class AppEntity extends AppLite {
    public String appType;
    public String appFileSize;
    public String appVersionNo;
    public String appIcon;
    public String appDescription;
    public String appScreenshots;

    public boolean isAndroidApp(){
        return 2 == Integer.parseInt(appType);
    }
}
