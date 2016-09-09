package com.moji.daypack.ui.app;

import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.data.source.AppRepository;
import com.moji.daypack.network.NetworkSubscriber;
import com.moji.daypack.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/21/16
 * Time: 10:23 PM
 * Desc: AppPresenter
 */
/* package */ class AppPresenter extends AbsAppPresenter<AppContract.View,Void> implements AppContract.Presenter<Void> {
    AppPresenter(AppRepository repository, AppContract.View view) {
        super(repository, view);
    }

    @Override
    public void subscribe(Void param) {
        loadAppList();
    }

    @Override
    public void loadAppList() {
        Subscription subscription = mRepository.apps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new NetworkSubscriber<List<IAppBasic>>(mView.getContext()) {
                    @Override
                    public void onStart() {
                        mView.onLoadAppStarted();
                    }

                    @Override
                    public void onNext(List<IAppBasic> apps) {
                        if(!AppUtils.isFilterIOS(mView.getContext())){
                            mView.onAppListLoaded(apps);
                        }else{
                            List<IAppBasic> appsTempList = new ArrayList<IAppBasic>();
                            for(IAppBasic app:apps){
                                if(AppUtils.isAndroidApp(app.getAppType())){
                                    appsTempList.add(app);
                                }
                            }
                            mView.onAppListLoaded(appsTempList);
                        }
                    }

                    @Override
                    public void onUnsubscribe() {
                        mView.onLoadAppCompleted();
                    }
                });
        mSubscriptions.add(subscription);
    }



}
