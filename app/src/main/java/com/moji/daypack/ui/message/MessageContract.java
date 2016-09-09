package com.moji.daypack.ui.message;

import android.content.Context;

import com.moji.daypack.data.model.Message;
import com.moji.daypack.ui.base.BaseView;

import java.util.List;

import com.moji.daypack.ui.base.BasePresenter;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/21/16
 * Time: 6:06 PM
 * Desc: MessageContract
 */
public interface MessageContract {

    interface View extends BaseView<Presenter> {

        Context getContext();

        void showMessages(List<Message> messages);

        void showOrHideEmptyView();

        void showLoadingIndicator();

        void hideLoadingIndicator();
    }

    interface Presenter<T> extends BasePresenter<T> {

        void loadMessages();
    }
}
