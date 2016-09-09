package com.moji.daypack.ui.app;

import android.support.v7.app.AppCompatActivity;

import com.moji.daypack.data.source.AppRepository;
import com.moji.daypack.util.AppUtils;
import com.moji.daypack.util.WarpUtils;

import java.util.ArrayList;
import java.util.List;

import com.moji.daypack.data.model.AppDetailModel;
import com.moji.daypack.data.model.AppLite;
import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.network.NetworkSubscriber;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GuDong on 2016/8/26 15:13.
 * Contact with ruibin.mao@moji.com.
 */
public class AppDetailPresenter extends AbsAppPresenter<AppDetailContract.View, String> implements AppDetailContract.Presenter<String> {
    private AppDetailModel mDetailModel;

    AppDetailPresenter(AppRepository repository, AppDetailContract.View view) {
        super(repository, view);
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
                        mDetailModel = appDetail;
                        List<IAppBasic> list = new ArrayList<IAppBasic>();
                        list.add(appDetail);
                        for (AppLite app : appDetail.otherapps) {
                            list.add(WarpUtils.warpAppLite(appDetail, app));
                        }
                        mView.onAppListLoaded(list);
                    }

                    @Override
                    public void onUnsubscribe() {
                        mView.onLoadAppCompleted();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void uninstall() {
        AppUtils.uninstallApp((AppCompatActivity) mView.getContext(), mDetailModel.appIdentifier);
    }

    @Override
    public boolean checkAppInstallStatus() {
        if(mDetailModel == null){
            return false;
        }
        if (AppUtils.isAndroidApp(mDetailModel.getAppType()) && AppUtils.isAppInstalled(mView.getContext(), mDetailModel.getAppIdentifier())) {
            return true;
        }
        return false;
    }

    @Override
    public void subscribe(String param) {
        loadAppDetail(param);
    }
}
