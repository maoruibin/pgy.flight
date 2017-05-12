package com.moji.daypack.ui.signin;

import com.moji.daypack.account.UserSession;

import rx.subscriptions.CompositeSubscription;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/13/16
 * Time: 9:55 PM
 * Desc: SignInPresenter
 */
public class SignInPresenter  implements SignInContract.Presenter {

    private static final String TAG = "SignInPresenter";

    SignInContract.View mView;
    CompositeSubscription mSubscriptions;

    public SignInPresenter(SignInContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void signIn(final String apiKey, final String userKey) {
        mView.onSignInStarted();
        UserSession.getInstance().signIn(apiKey, userKey);
        mView.onSignInCompleted();
    }

    @Override
    public void subscribe(Object param) {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
        mView = null;
    }
}
