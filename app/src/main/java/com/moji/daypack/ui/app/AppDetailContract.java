package com.moji.daypack.ui.app;

/**
 * Created by GuDong on 2016/8/26 12:38.
 * Contact with ruibin.mao@moji.com.
 */
interface AppDetailContract {
    interface View<T> extends AbsAppListContract.View<T,Presenter<T>> {

    }
    interface Presenter<T> extends AbsAppListContract.Presenter<T> {

        void loadAppDetail(String appKey);

        void uninstall();

        /**
         * 检测应用是否安装
         * @return true 已安装
         */
        boolean checkAppInstallStatus();
    }
}
