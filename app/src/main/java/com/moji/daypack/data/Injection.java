package com.moji.daypack.data;

import android.content.Context;

import com.moji.daypack.data.source.remote.api.ApiService;
import com.moji.daypack.network.RetrofitClient;
import com.moji.daypack.FlightApplication;
import com.moji.daypack.data.source.remote.api.RESTFulApiService;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:48 AM
 * Desc: Injection
 */
public class Injection {

    public static Context provideContext() {
        return FlightApplication.getInstance();
    }

    public static RESTFulApiService provideRESTfulApi() {
        return RetrofitClient.defaultInstance().create(RESTFulApiService.class);
    }

    public static ApiService provideApi() {
        return RetrofitClient.defaultInstancePgy().create(ApiService.class);
    }
}
