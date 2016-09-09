package com.moji.daypack.ui.message;

import com.moji.daypack.network.NetworkSubscriber;

import java.util.List;

import com.moji.daypack.data.model.Message;
import com.moji.daypack.data.source.MessageRepository;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/21/16
 * Time: 11:23 PM
 * Desc: MessagePresenter
 */
public class MessagePresenter implements MessageContract.Presenter<Void> {

    MessageContract.View mView;
    MessageRepository mRepository;
    CompositeSubscription mSubscriptions;

    public MessagePresenter(MessageRepository repository, MessageContract.View view) {
        mRepository = repository;
        mView  = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadMessages() {
        mView.showLoadingIndicator();
        Subscription subscription = mRepository.systemMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new NetworkSubscriber<List<Message>>(mView.getContext()){
                    @Override
                    public void onNext(List<Message> messages) {
                        mView.showMessages(messages);
                    }

                    @Override
                    public void onUnsubscribe() {
                        mView.showOrHideEmptyView();
                        mView.hideLoadingIndicator();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe(Void param) {
        loadMessages();
    }

    @Override
    public void unsubscribe() {
        mView = null;
        mSubscriptions.clear();
    }
}
