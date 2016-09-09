package com.moji.daypack.ui.app;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.ui.base.BasePresenter;
import com.moji.daypack.ui.base.BaseView;

/**
 * Created by GuDong on 9/9/16 00:39.
 * Contact with gudong.name@gmail.com.
 */
public interface AbsAppListContract {

    interface View<T,P extends AbsAppListContract.Presenter<T>> extends BaseView<P> {

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

    interface Presenter<T> extends BasePresenter<T> {

        void requestInstallUrl(final AppItemView itemView, final int position);

    }

}
