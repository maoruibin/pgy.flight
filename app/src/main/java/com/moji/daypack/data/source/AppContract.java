package com.moji.daypack.data.source;

import com.moji.daypack.data.model.Bean;
import com.moji.daypack.data.model.IAppBasic;

import java.util.List;

import com.moji.daypack.data.model.AppDetailModel;
import com.moji.daypack.data.model.AppEntity;
import com.moji.daypack.data.model.AppPgy;

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

    Observable<List<IAppBasic>> filterToday();

}
