package io.github.ryanhoo.firFlight.ui.app;

import android.os.Environment;

import java.io.File;
import java.util.List;

import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.source.AppRepository;
import io.github.ryanhoo.firFlight.network.NetworkSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by GuDong on 2016/8/26 15:13.
 * Contact with ruibin.mao@moji.com.
 */
public class AppDetailPresenter implements AppDetailContract.Presenter {
    private static File DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

    private AppRepository mRepository;
    private AppDetailContract.View mView;
    private CompositeSubscription mSubscriptions;

    /* package */ AppDetailPresenter(AppRepository repository, AppDetailContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadApps(String appKey) {
        Subscription subscription = mRepository.appView(appKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new NetworkSubscriber<List<AppEntity>>(mView.getContext()) {
                    @Override
                    public void onStart() {
                        mView.onLoadAppStarted();
                    }

                    @Override
                    public void onNext(List<AppEntity> apps) {
                        mView.onAppsLoaded(apps);
                    }

                    @Override
                    public void onUnsubscribe() {
                        mView.onLoadAppCompleted();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void requestInstallUrl(AppItemView itemView, int position) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
