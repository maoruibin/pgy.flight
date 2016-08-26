package io.github.ryanhoo.firFlight.data.source;

import io.github.ryanhoo.firFlight.data.model.App;
import io.github.ryanhoo.firFlight.data.model.AppDetail;
import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppInstallInfo;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import rx.Observable;

import java.util.List;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:17 AM
 * Desc: AppContract
 */
public interface AppContract {

    interface Local {

        Observable<List<AppEntity>> apps();

        boolean save(AppEntity app);

        int save(List<AppEntity> apps);

        boolean delete(AppEntity app);

        int delete(List<AppEntity> apps);

        int deleteAll();
    }

    interface Remote {

        Observable<AppPgy> apps();

        Observable<AppDetail> appView(String appKey);
    }

    Observable<List<AppEntity>> apps();

    Observable<List<AppEntity>> appView(String appKey);

    Observable<List<AppEntity>> apps(boolean forceUpdate);

}
