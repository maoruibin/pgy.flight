package io.github.ryanhoo.firFlight.data.source.remote.api;

import io.github.ryanhoo.firFlight.data.model.AppDetailModel;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.model.Bean;
import io.github.ryanhoo.firFlight.data.model.Token;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    // Token

    @FormUrlEncoded
    @POST("/login")
    Observable<Token> accessToken(@Field("email") String email, @Field("password") String password);

    @GET("/user/api_token")
    Observable<Token> apiToken();

    @POST("/user/api_token")
    Observable<Token> refreshApiToken();

    @FormUrlEncoded
    @POST("/apiv1/user/listMyPublished")
    Observable<AppPgy> apps(@Field("uKey") String uKey, @Field("page") int page, @Field("_api_key") String _api_key);

    @FormUrlEncoded
    @POST("/apiv1/app/view")
    Observable<Bean<AppDetailModel>> view(@Field("aKey") String aKey, @Field("uKey") String uKey, @Field("_api_key") String _api_key);

}
