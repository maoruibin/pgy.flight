package io.github.ryanhoo.firFlight.data.source.remote;

import java.util.List;

import io.github.ryanhoo.firFlight.data.model.App;
import io.github.ryanhoo.firFlight.data.model.AppDetail;
import io.github.ryanhoo.firFlight.data.model.AppInstallInfo;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.source.AppContract;
import io.github.ryanhoo.firFlight.data.source.remote.api.ApiService;
import io.github.ryanhoo.firFlight.data.source.remote.api.RESTFulApiService;
import rx.Observable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:44 AM
 * Desc: RemoteAppDataSource
 */
public class RemoteAppDataSource implements AppContract.Remote {
    private static final String KEY_API = "2332aa84039a5bbe12f9dfdc110e64f6";
    private static final String KEY_USER = "3172bed7694c12e7336ca602d0c158bb";
    protected ApiService mApi;

    public RemoteAppDataSource(ApiService api) {
        mApi = api;
    }

    @Override
    public Observable<AppPgy> apps() {
        return mApi.apps(KEY_USER,1,KEY_API);
    }

    @Override
    public Observable<AppDetail> appView(String appKey) {
        return mApi.view(appKey,KEY_USER,KEY_API);
    }

    public static String makeDownloadUrl(String appKey){
        return "http://www.pgyer.com/apiv1/app/install?aKey="+appKey+"&_api_key="+KEY_API;
    }
}