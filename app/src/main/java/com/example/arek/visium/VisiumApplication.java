package com.example.arek.visium;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.UiThread;

import com.example.arek.visium.dependency_injection.application.DaggerVisiumApplicationComponent;
import com.example.arek.visium.dependency_injection.application.VisiumApplicationComponent;
import com.example.arek.visium.dependency_injection.application.ContextModule;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.screens.image_duel.ImageDuelPresenter;
import com.example.arek.visium.screens.register.RegisterActivityPresenterImpl;
import com.example.arek.visium.screens.user_preferences.UserPreferencesRepository;
import com.example.arek.visium.screens.user_preferences.UserPreferencesRepositoryImpl;
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
   private RegisterActivityPresenterImpl registerActivityPresenterImpl;
   private VisiumService visiumService;

   private Retrofit retrofit;
   private static Context context = null;
   private UserPreferencesRepository userPreferencesRepository;

   private VisiumApplicationComponent visiumApplicationComponent;
   private Picasso picasso;

    @UiThread
    public VisiumApplicationComponent getVisiumApplicationComponent(){
        return visiumApplicationComponent;
    }

   public static VisiumApplication get(Activity activity){
       return (VisiumApplication)activity.getApplication();
   }

   private ImageDuelPresenter imageDuelPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        visiumApplicationComponent = DaggerVisiumApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        visiumService = visiumApplicationComponent.getVisiumService();
        picasso = visiumApplicationComponent.getPicasso();

        initRealmConfiguration();

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
//        registerActivityPresenterImpl = new RegisterActivityPresenterImpl(userStorage);
//        userPreferencesRepository = new UserPreferencesRepositoryImpl();
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

    public RegisterActivityPresenterImpl getRegisterActivityPresenterImpl(){
        return registerActivityPresenterImpl;
    }
    public UserStorage getUserStorage() {
        return userStorage;
    }

    public VisiumService getVisiumService(){
        return visiumService;
    }

    public ImageDuelPresenter getImageDuelPresenter() {
        return imageDuelPresenter;
    }

    public static Context getContext() {
        return context;
    }

    public UserPreferencesRepository getUserPreferencesRepository() {
        return userPreferencesRepository;
    }
}