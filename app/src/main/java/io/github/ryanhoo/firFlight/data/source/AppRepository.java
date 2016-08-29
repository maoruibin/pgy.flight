package io.github.ryanhoo.firFlight.data.source;

import java.util.ArrayList;
import java.util.List;

import io.github.ryanhoo.firFlight.data.Injection;
import io.github.ryanhoo.firFlight.data.model.AppDetailModel;
import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.model.Bean;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.data.source.local.LocalAppDataSource;
import io.github.ryanhoo.firFlight.data.source.remote.RemoteAppDataSource;
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
        return  mRemoteDataSourcePgy.appView(appKey)
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
        //Observable<List<AppEntity>> local = mLocalDataSource.apps();
        Observable<List<IAppBasic>>remote = mRemoteDataSourcePgy.apps().map(new Func1<AppPgy, List<IAppBasic>>() {
            @Override
            public List<IAppBasic> call(AppPgy appPgy) {
                List<AppEntity> list = appPgy.data.list;
                List<IAppBasic> finalAndroidAppList = new ArrayList<IAppBasic>();
                for(AppEntity app:list ){
                    finalAndroidAppList.add(app);
                }
                return finalAndroidAppList;
            }
        });

        if (forceUpdate) {
            return remote;
        }
        return remote;
        //return Observable.concat(local.first(), remote);
    }
}
