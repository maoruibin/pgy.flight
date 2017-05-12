package com.moji.daypack.ui.signin;

import android.content.Context;

import com.moji.daypack.ui.base.BasePresenter;
import com.moji.daypack.ui.base.BaseView;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/13/16
 * Time: 9:54 PM
 * Desc: SignInContract
 */
public interface SignInContract {

    interface View extends BaseView<Presenter> {

        Context getContext();

        void onSignInStarted();

        void onSignInCompleted();

        void onSignInError(Throwable e);
    }

    interface Presenter extends BasePresenter {

        void signIn(String email, String password);
    }
}
