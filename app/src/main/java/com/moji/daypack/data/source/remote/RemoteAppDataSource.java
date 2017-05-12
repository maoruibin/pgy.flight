package com.moji.daypack.data.source.remote;

import com.moji.daypack.data.model.Bean;
import com.moji.daypack.data.model.Token;
import com.moji.daypack.data.source.TokenRepository;
import com.moji.daypack.data.source.remote.api.ApiService;
import com.moji.daypack.network.ServerConfig;
import com.moji.daypack.data.model.AppDetailModel;
import com.moji.daypack.data.model.AppPgy;
import com.moji.daypack.data.source.AppContract;

import rx.Observable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:44 AM
 * Desc: RemoteAppDataSource
 */
public class RemoteAppDataSource implements AppContract.Remote {
    protected ApiService mApi;
    private static Token mToken;
    static {
        mToken = TokenRepository.getInstance().restoreToken();
    }

    public RemoteAppDataSource(ApiService api) {
        mApi = api;
    }

    @Override
    public Observable<AppPgy> apps() {
        return mApi.apps(mToken.getUserKey(),1,mToken.getApiKey());
    }

    @Override
    public Observable<Bean<AppDetailModel>> appView(String appKey) {
        return mApi.view(appKey,mToken.getUserKey(),mToken.getApiKey());
    }

    public static String makeDownloadUrl(String appKey){
        return ServerConfig.API_HOST_PGY+"/apiv1/app/install?aKey="+appKey+"&_api_key="+mToken.getApiKey();
    }
}
