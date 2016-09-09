package com.moji.daypack.ui.app;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/21/16
 * Time: 10:17 PM
 * Desc: AppContract
 */

interface AppContract {

    interface View<T> extends AbsAppListContract.View<T,Presenter<T>> {

    }

    interface Presenter<T> extends AbsAppListContract.Presenter<T>  {
        void loadAppList();
    }
}
