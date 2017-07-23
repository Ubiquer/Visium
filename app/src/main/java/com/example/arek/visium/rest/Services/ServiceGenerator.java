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

    @Override
    public void update(Observable o, Object arg) {

    }
}
