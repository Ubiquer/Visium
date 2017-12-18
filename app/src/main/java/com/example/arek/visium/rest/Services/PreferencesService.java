package com.example.arek.visium.rest.Services;

import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.rest.VisiumService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arek on 7/21/2017.
 */

public class PreferencesService {

    private static volatile PreferencesService mPreferencesService;
    private final VisiumService mVisiumService;

    private PreferencesService(){

        mVisiumService = new Retrofit.Builder()
                .baseUrl(ApiKeys.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(VisiumService.class);

    }

    public static PreferencesService getInstance(){

        if (mPreferencesService == null){
            synchronized (PreferencesService.class){
                if (mPreferencesService == null){
                    mPreferencesService = new PreferencesService();
                }
            }
        }

        return mPreferencesService;

    }

}
