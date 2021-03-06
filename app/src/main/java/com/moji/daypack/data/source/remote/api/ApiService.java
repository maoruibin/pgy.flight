package com.moji.daypack.data.source.remote.api;

import com.moji.daypack.data.model.AppDetailModel;
import com.moji.daypack.data.model.AppPgy;
import com.moji.daypack.data.model.Bean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 5/31/16
 * Time: 11:10 PM
 * Desc: RESTFulApiService
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("/apiv1/user/listMyPublished")
    Observable<AppPgy> apps(@Field("uKey") String uKey, @Field("page") int page, @Field("_api_key") String _api_key);

    @FormUrlEncoded
    @POST("/apiv1/app/view")
    Observable<Bean<AppDetailModel>> view(@Field("aKey") String aKey, @Field("uKey") String uKey, @Field("_api_key") String _api_key);

}
