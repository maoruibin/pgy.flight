package io.github.ryanhoo.firFlight.util;

import io.github.ryanhoo.firFlight.data.model.AppDetailModel;
import io.github.ryanhoo.firFlight.data.model.AppLite;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;

/**
 * Created by GuDong on 8/28/16 23:03.
 * Contact with gudong.name@gmail.com.
 */
public class WarpUtils {
    public static IAppBasic warpAppLite(final AppDetailModel model,final AppLite entity){
        return new IAppBasic() {
            @Override
            public String getAppIcon() {
                return model.appIcon;
            }

            @Override
            public String getAppKey() {
                return entity.getAppKey();
            }

            @Override
            public String getAppName() {
                return entity.appName;
            }

            @Override
            public String getAppVersion() {
                return entity.appVersion;
            }

            @Override
            public String getAppBuildVersion() {
                return entity.appBuildVersion;
            }

            @Override
            public String getAppIdentifier() {
                return entity.appIdentifier;
            }

            @Override
            public String getAppUpdateDescription() {
                return entity.appUpdateDescription;
            }

            @Override
            public String getAppType() {
                return model.appType;
            }

            @Override
            public String getAppCreated() {
                return entity.appCreated;
            }

            @Override
            public long getAppFileSize() {
                return model.appFileSize;
            }

            @Override
            public boolean isAppLatest() {
                return false;
            }
        };
    }
}
