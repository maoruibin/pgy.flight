package io.github.ryanhoo.firFlight.data.source;

import java.util.List;

import io.github.ryanhoo.firFlight.data.model.AppDetailModel;
import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.model.Bean;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import rx.Observable;

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

        Observable<Bean<AppDetailModel>> appView(String appKey);
    }

    Observable<List<IAppBasic>> apps();

    Observable<AppDetailModel> appView(String appKey);

    Observable<List<IAppBasic>> apps(boolean forceUpdate);

}
