package com.moji.daypack.ui.profile;

import android.content.Context;

import com.moji.daypack.data.model.Token;
import com.moji.daypack.data.model.User;
import com.moji.daypack.ui.base.BasePresenter;
import com.moji.daypack.ui.base.BaseView;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/9/16
 * Time: 12:21 AM
 * Desc: ProfileContract
 */
public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        Context getContext();

        void updateUserProfile(User user);

        void updateApiToken(Token token);

        void onRefreshApiTokenStart();

        void onRefreshApiTokenCompleted();
    }

    interface Presenter<T> extends BasePresenter<T> {

        void refreshUserProfile();

        void refreshApiToken();
    }
}
