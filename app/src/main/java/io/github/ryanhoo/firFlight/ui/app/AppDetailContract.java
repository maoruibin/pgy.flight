package io.github.ryanhoo.firFlight.ui.app;

/**
 * Created by GuDong on 2016/8/26 12:38.
 * Contact with ruibin.mao@moji.com.
 */
interface AppDetailContract {
    interface View extends AbsAppListContract.View<Presenter> {

    }
    interface Presenter extends AbsAppListContract.Presenter {

        void loadAppDetail(String appKey);

        void uninstall();
    }
}
