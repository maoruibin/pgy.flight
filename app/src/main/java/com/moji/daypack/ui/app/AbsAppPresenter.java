package com.moji.daypack.ui.app;

import android.net.Uri;
import android.os.Environment;

import com.moji.daypack.data.source.AppRepository;
import com.moji.daypack.util.AppUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.data.source.remote.RemoteAppDataSource;
import com.moji.daypack.network.NetworkSubscriber;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by GuDong on 9/9/16 09:10.
 * Contact with gudong.name@gmail.com.
 */
public abstract class AbsAppPresenter<V extends AbsAppListContract.View,T> implements AbsAppListContract.Presenter<T>{
    private static File DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

    protected AppRepository mRepository;
    protected V mView;
    protected CompositeSubscription mSubscriptions;

    AbsAppPresenter(AppRepository repository, V view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void requestInstallUrl(final AppItemView itemView, final int position) {
        final AppInfo appInfo = itemView.appInfo;
        final IAppBasic app = appInfo.app;
        Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(RemoteAppDataSource.makeDownloadUrl(app.getAppKey()));
                subscriber.onCompleted();
            }
        })
                .flatMap(new Func1<String, Observable<AppDownloadTask.DownloadInfo>>() {
                    @Override
                    public Observable<AppDownloadTask.DownloadInfo> call(String downloadUrl) {
                        AppDownloadTask task = new AppDownloadTask(downloadUrl, AppUtils.getInstalledApkFile(app));
                        mView.addTask(app.getAppKey(), task);
                        return task.downloadApk();
                    }
                })
                // In case back pressure exception, only emit 1 onNext event in 16ms(screen drawing interval)
                .debounce(16, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkSubscriber<AppDownloadTask.DownloadInfo>(mView.getContext()) {
                    @Override
                    public void onStart() {
                        itemView.buttonAction.setEnabled(false);
                    }

                    @Override
                    public void onNext(AppDownloadTask.DownloadInfo info) {
                        mView.updateAppInfo(app.getAppKey(), position);
                        if (info.progress == 1f) {
                            mView.installApk(Uri.fromFile(info.apkFile));
                        }
                    }

                    @Override
                    public void onUnsubscribe() {
                        mView.removeTask(app.getAppKey());
                        itemView.buttonAction.setEnabled(true);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
        mView = null;
    }
}
