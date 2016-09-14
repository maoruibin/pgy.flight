package com.moji.daypack.data.source.remote;

import com.moji.daypack.data.source.remote.api.RESTFulApiService;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:45 AM
 * Desc: AbstractRemoteDataSource
 */
public abstract class AbstractRemoteDataSource {

    protected RESTFulApiService mApi;

    public AbstractRemoteDataSource(RESTFulApiService api) {
        mApi = api;
    }
}