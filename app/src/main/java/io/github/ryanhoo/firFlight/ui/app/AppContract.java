package io.github.ryanhoo.firFlight.ui.app;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/21/16
 * Time: 10:17 PM
 * Desc: AppContract
 */

interface AppContract {

    interface View extends AbsAppListContract.View<Presenter>{

    }

    interface Presenter extends AbsAppListContract.Presenter  {
        void loadAppList();
    }
}
