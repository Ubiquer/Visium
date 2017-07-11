package com.example.arek.visium.rest;

import android.text.TextUtils;

import java.util.Observable;
import java.util.Observer;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arek on 2017-06-25.
 */

public class ServiceGenerator implements Observer{

    private static Retrofit retrofit = null;
    private static OkHttpClient httpClient = new OkHttpClient();

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//            httpClient.addNetworkInterceptor(new SessionRequestInterceptor());
//            httpClient.addNetworkInterceptor(new ReceivedCookiesInterceptor());

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

//    public static <S> S createService(
//            Class<S> serviceClass, final String authToken) {
//        if (!TextUtils.isEmpty(authToken)) {
//            AuthenticationInterceptor interceptor =
//                    new AuthenticationInterceptor(authToken);
//
//            if (!httpClient.interceptors().contains(interceptor)) {
//                httpClient.addInterceptor(interceptor);
//
//                builder.client(httpClient.build());
//                retrofit = builder.build();
//            }
//        }
//
//        return retrofit.create(serviceClass);
//    }
//
//    public static Retrofit postImage(String url){
//        if (retrofit == null){
//
//
//
//
//        }
//        return retrofit;
//    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
