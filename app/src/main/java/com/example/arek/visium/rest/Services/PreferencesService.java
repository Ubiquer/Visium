package com.example.arek.visium.rest.Services;

import com.example.arek.visium.rest.IntentKeys;
import com.example.arek.visium.rest.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arek on 7/21/2017.
 */

public class PreferencesService {

    private static volatile PreferencesService mPreferencesService;
    private final ApiInterface mApiInterface;

    private PreferencesService(){

        mApiInterface = new Retrofit.Builder()
                .baseUrl(IntentKeys.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);

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
