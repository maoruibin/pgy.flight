package io.github.ryanhoo.firFlight.ui.app;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import io.github.ryanhoo.firFlight.data.model.AppDetailModel;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.ui.base.BasePresenter;
import io.github.ryanhoo.firFlight.ui.base.BaseView;

/**
 * Created by GuDong on 2016/8/26 12:38.
 * Contact with ruibin.mao@moji.com.
 */
interface AppDetailContract {
    interface View extends BaseView<Presenter> {
        Context getContext();

        void onFillAppDetail(AppDetailModel detail);
        void onFillHistList(List<IAppBasic> apps);

        void onLoadAppStarted();

        void onLoadAppCompleted();

        // Download app

        void addTask(String appId, AppDownloadTask task);

        void removeTask(String appId);

        void updateAppInfo(String appId, int position);

        void installApk(Uri apkUri);

    }
    interface Presenter extends BasePresenter {

        void loadAppDetail(String appKey);

        void requestInstallUrl(final AppItemView itemView, final int position);

        void uninstall();
    }
}
