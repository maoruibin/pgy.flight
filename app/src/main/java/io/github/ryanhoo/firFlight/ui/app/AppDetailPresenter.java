package io.github.ryanhoo.firFlight.ui.app;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.ryanhoo.firFlight.data.model.AppDetailModel;
import io.github.ryanhoo.firFlight.data.model.AppLite;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.data.source.AppRepository;
import io.github.ryanhoo.firFlight.data.source.remote.RemoteAppDataSource;
import io.github.ryanhoo.firFlight.network.NetworkSubscriber;
import io.github.ryanhoo.firFlight.ui.base.BaseActivity;
import io.github.ryanhoo.firFlight.util.AppUtils;
import io.github.ryanhoo.firFlight.util.WarpUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
    private AppDetailModel detailModel;

    /* package */ AppDetailPresenter(AppRepository repository, AppDetailContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadAppDetail(String appKey) {
        Subscription subscription = mRepository.appView(appKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new NetworkSubscriber<AppDetailModel>(mView.getContext()) {
                    @Override
                    public void onStart() {
                        mView.onLoadAppStarted();
                    }

                    @Override
                    public void onNext(AppDetailModel appDetail) {
                        detailModel = appDetail;
                        List<IAppBasic> list = new ArrayList<IAppBasic>();
                        for(AppLite app:appDetail.otherapps){
                            list.add(WarpUtils.warpAppLite(appDetail,app));
                        }
                        mView.onFillHistList(list);
                    }

                    @Override
                    public void onUnsubscribe() {
                        mView.onLoadAppCompleted();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void requestInstallUrl(final AppItemView itemView,final int position) {
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
                        AppDownloadTask task = new AppDownloadTask(downloadUrl);
                        mView.addTask(app.getAppKey(), task);
                        return task.downloadApk(DOWNLOAD_DIR);
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
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void uninstall() {
        AppUtils.uninstallApp((BaseActivity)mView.getContext(),detailModel.appIdentifier);
    }
}
