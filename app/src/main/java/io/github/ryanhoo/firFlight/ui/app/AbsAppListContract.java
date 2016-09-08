package io.github.ryanhoo.firFlight.ui.app;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.ui.base.BasePresenter;
import io.github.ryanhoo.firFlight.ui.base.BaseView;

/**
 * Created by GuDong on 9/9/16 00:39.
 * Contact with gudong.name@gmail.com.
 */
public interface AbsAppListContract {

    interface View<P extends AbsAppListContract.Presenter> extends BaseView<P> {

        Context getContext();

        // Load apps

        void onAppListLoaded(List<IAppBasic> apps);

        void onLoadAppStarted();

        void onLoadAppCompleted();

        // Download app

        void addTask(String appId, AppDownloadTask task);

        void removeTask(String appId);

        void updateAppInfo(String appId, int position);

        void installApk(Uri apkUri);
    }

    interface Presenter extends BasePresenter {

        void requestInstallUrl(final AppItemView itemView, final int position);

    }

}
