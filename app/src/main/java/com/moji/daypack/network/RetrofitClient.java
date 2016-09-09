package com.moji.daypack.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.moji.daypack.data.model.IMessageContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import com.moji.daypack.FlightApplication;
import com.moji.daypack.network.gson.DateDeserializer;
import com.moji.daypack.network.gson.MessageContentDeserializer;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/15/16
 * Time: 10:58 PM
 * Desc: RetrofitClient
 */
public class RetrofitClient {
    public static Retrofit defaultInstance() {
        return new Retrofit.Builder()
                .client(defaultOkHttpClient())
                .baseUrl(ServerConfig.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(defaultGson()))

                .build();
    }

    public static Retrofit defaultInstancePgy() {
        return new Retrofit.Builder()
                .client(defaultOkHttpClient())
                .baseUrl(ServerConfig.API_HOST_PGY)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(defaultGson()))
                .build();
    }

    public static OkHttpClient defaultOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new SessionInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                // Ugly context injection
                .addNetworkInterceptor(new MockInterceptor(FlightApplication.getInstance()))
                .build();
    }

    public static Gson defaultGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
//                .registerTypeAdapter(Date.class, new PgyDateDeserializer())
                .registerTypeAdapter(IMessageContent.class, new MessageContentDeserializer())
                .create();
    }
}
