package com.moji.daypack.data.source.remote;

import com.moji.daypack.data.source.UserContract;
import com.moji.daypack.data.model.User;
import com.moji.daypack.data.source.remote.api.RESTFulApiService;
import rx.Observable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/2/16
 * Time: 10:44 PM
 * Desc: RemoteUserDataSource
 */
public class RemoteUserDataSource extends AbstractRemoteDataSource implements UserContract.Remote {

    public RemoteUserDataSource(RESTFulApiService api) {
        super(api);
    }

    @Override
    public Observable<User> user() {
        return mApi.user();
    }
}
