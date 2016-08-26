package io.github.ryanhoo.firFlight.data.source;

import io.github.ryanhoo.firFlight.data.Injection;
import io.github.ryanhoo.firFlight.data.model.AppDetail;
import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.source.local.LocalAppDataSource;
import io.github.ryanhoo.firFlight.data.source.remote.RemoteAppDataSource;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

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
    public Observable<List<AppEntity>> apps() {
        return apps(false);
    }

    @Override
    public Observable<List<AppEntity>> appView(String appKey) {
        return  mRemoteDataSourcePgy.appView(appKey).map(new Func1<AppDetail, List<AppEntity>>() {
            @Override
            public List<AppEntity> call(AppDetail appDetail) {
                return new ArrayList<AppEntity>();
            }
        });
    }

    @Override
    public Observable<List<AppEntity>> apps(boolean forceUpdate) {
        //Observable<List<AppEntity>> local = mLocalDataSource.apps();
        Observable<List<AppEntity>>remote = mRemoteDataSourcePgy.apps().map(new Func1<AppPgy, List<AppEntity>>() {
            @Override
            public List<AppEntity> call(AppPgy appPgy) {
                List<AppEntity> list = appPgy.data.list;
                List<AppEntity> finalAndroidAppList = new ArrayList<AppEntity>();
                for(AppEntity app:list ){
                    if(app.isAndroidApp()){
                        finalAndroidAppList.add(app);
                    }
                }
                return finalAndroidAppList;
            }
        }).doOnNext(new Action1<List<AppEntity>>() {
            @Override
            public void call(List<AppEntity> apps) {
                //mLocalDataSource.deleteAll();
               // mLocalDataSource.save(apps);
            }
        });;

        if (forceUpdate) {
            return remote;
        }
        return remote;
        //return Observable.concat(local.first(), remote);
    }
}
