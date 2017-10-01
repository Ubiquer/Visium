package com.example.arek.visium;

import android.app.Application;
import android.preference.PreferenceManager;

import com.example.arek.visium.rest.ApiInterface;
import com.example.arek.visium.rest.IntentKeys;
import com.example.arek.visium.screens.login.LoginManager;
import com.example.arek.visium.screens.register.RegisterManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arek on 7/14/2017.
 */

public class VisiumApplication extends Application {

   private LoginManager loginManager;
   private UserStorage userStorage;
   private RegisterManager registerManager;
   private ApiInterface apiInterface;
   private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(IntentKeys.BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        retrofit = builder.build();
        apiInterface = retrofit.create(ApiInterface.class);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        loginManager = new LoginManager(userStorage, apiInterface, retrofit);
        registerManager = new RegisterManager(userStorage, apiInterface, retrofit);
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public RegisterManager getRegisterManager(){
        return registerManager;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public ApiInterface getApiInterface(){
        return apiInterface;
    }
}