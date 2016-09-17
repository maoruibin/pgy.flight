package com.moji.daypack.data.source;

import android.util.Log;

import com.moji.daypack.data.Injection;
import com.moji.daypack.data.model.AppDetailModel;
import com.moji.daypack.data.model.AppEntity;
import com.moji.daypack.data.model.AppPgy;
import com.moji.daypack.data.model.Bean;
import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.data.source.local.LocalAppDataSource;
import com.moji.daypack.data.source.remote.RemoteAppDataSource;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:47 AM
 * Desc: AppRepository
 */
public class AppRepository implements AppContract {
    private static final String TAG = "AppRepository";
    private static AppRepository sInstance;

    AppContract.Local mLocalDataSource;
    AppContract.Remote mRemoteDataSourcePgy;

    private AppRepository() {
        mLocalDataSource = new LocalAppDataSource(Injection.provideContext());
        mRemoteDataSourcePgy = new RemoteAppDataSource(Injection.provideApi());
    }

    public static AppRepository getInstance() {
        if (sInstance == null) {
            synchronized (AppRepository.class) {
                if (sInstance == null) {
                    sInstance = new AppRepository();
                }
            }
        }
        return sInstance;
    }

    @Override
    public Observable<List<IAppBasic>> apps() {
        return apps(false);
    }

    @Override
    public Observable<AppDetailModel> appView(String appKey) {
        return mRemoteDataSourcePgy.appView(appKey)
                .filter(new Func1<Bean<AppDetailModel>, Boolean>() {
                    @Override
                    public Boolean call(Bean<AppDetailModel> appDetailModelBean) {
                        return appDetailModelBean.code == 0;
                    }
                })
                .map(new Func1<Bean<AppDetailModel>, AppDetailModel>() {
                    @Override
                    public AppDetailModel call(Bean<AppDetailModel> appDetailModelBean) {
                        return appDetailModelBean.data;
                    }
                });
    }

    @Override
    public Observable<List<IAppBasic>> apps(boolean forceUpdate) {
        Observable<List<IAppBasic>> local = mLocalDataSource.apps()
                .map(new Func1<List<AppEntity>, List<IAppBasic>>() {
                    @Override
                    public List<IAppBasic> call(List<AppEntity> list) {
                        List<IAppBasic> finalAndroidAppList = new ArrayList<IAppBasic>();
                        for (AppEntity app : list) {
                            finalAndroidAppList.add(app);
                        }
                        return finalAndroidAppList;
                    }
                });
        Observable<List<IAppBasic>> remote = mRemoteDataSourcePgy.apps()
        .map(new Func1<AppPgy, List<IAppBasic>>() {
                    @Override
                    public List<IAppBasic> call(AppPgy appPgy) {
                        List<AppEntity> list = appPgy.data.list;
                        mLocalDataSource.deleteAll();
                        mLocalDataSource.save(list);
                        Log.i(TAG,"save success");
                        List<IAppBasic> finalAndroidAppList = new ArrayList<IAppBasic>();
                        for (AppEntity app : list) {
                            finalAndroidAppList.add(app);
                        }
                        return finalAndroidAppList;
                    }
                });
        if (forceUpdate) {
            Log.i(TAG,"use remote only");
            return remote;
        }
        Log.i(TAG,"use local and remote only");
        return Observable.concat(local.first(), remote);
    }


    @Override
    public Observable<List<IAppBasic>> filterToday() {
        Observable<List<IAppBasic>> remote = mRemoteDataSourcePgy.apps().map(new Func1<AppPgy, List<IAppBasic>>() {
            @Override
            public List<IAppBasic> call(AppPgy appPgy) {
                List<AppEntity> list = appPgy.data.list;
                List<IAppBasic> finalAndroidAppList = new ArrayList<IAppBasic>();
                for (AppEntity app : list) {
//                    if(DateUtils.isToday(app.appCreated)){
//                        finalAndroidAppList.add(app);
//                    }
                }
                return finalAndroidAppList;
            }
        });
        return remote;
    }
}
