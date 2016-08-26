package io.github.ryanhoo.firFlight.ui.app;

import android.content.Context;

import java.util.List;

import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.ui.base.BasePresenter;
import io.github.ryanhoo.firFlight.ui.base.BaseView;

/**
 * Created by GuDong on 2016/8/26 12:38.
 * Contact with ruibin.mao@moji.com.
 */
interface AppDetailContract {
    interface View extends BaseView<Presenter> {
        Context getContext();
        // Load apps

        void onAppsLoaded(List<AppEntity> apps);

        void onLoadAppStarted();

        void onLoadAppCompleted();

    }
    interface Presenter extends BasePresenter {

        void loadApps(String appKey);

        void requestInstallUrl(final AppItemView itemView, final int position);
    }
}
