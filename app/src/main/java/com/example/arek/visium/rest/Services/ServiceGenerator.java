package com.example.arek.visium.rest.Services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arek on 2017-06-25.
 */

public class ServiceGenerator{

    private static Retrofit retrofit = null;
    private static OkHttpClient httpClient = new OkHttpClient();

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    
}
