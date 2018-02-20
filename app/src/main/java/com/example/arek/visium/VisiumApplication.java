package com.example.arek.visium;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.example.arek.visium.dependency_injection.application.DaggerVisiumApplicationComponent;
import com.example.arek.visium.dependency_injection.application.VisiumApplicationComponent;
import com.example.arek.visium.dependency_injection.application.ApplicationModule;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.screens.register.RegisterActivityPresenterImpl;
import com.squareup.picasso.Picasso;

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

   private UserStorage userStorage;
   private VisiumService visiumService;
   private RealmService realmService;

   private Retrofit retrofit;
   private static Context context = null;

   private VisiumApplicationComponent visiumApplicationComponent;
   private Picasso picasso;

    public VisiumApplicationComponent getVisiumApplicationComponent(){
        return visiumApplicationComponent;
    }

   public static VisiumApplication get(Activity activity){
       return (VisiumApplication)activity.getApplication();
   }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        initRealmConfiguration();

        visiumApplicationComponent = DaggerVisiumApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        visiumService = visiumApplicationComponent.getVisiumService();
        picasso = visiumApplicationComponent.getPicasso();
        realmService = visiumApplicationComponent.getRealmService();

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(ApiKeys.BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        retrofit = builder.build();
        visiumService = retrofit.create(VisiumService.class);
        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
    }

    private void initRealmConfiguration(){

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public VisiumApplicationComponent component(){
        return visiumApplicationComponent;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public VisiumService getVisiumService(){
        return visiumService;
    }

    public static Context getContext() {
        return context;
    }

    public RealmService getRealmService() {
        return realmService;
    }

}