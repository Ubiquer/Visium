package com.example.arek.visium.dependency_injection.application;

import android.content.Context;

import com.example.arek.visium.RealmService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by arek on 12/4/2017.
 */
@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @VisiumApplicationScope
    @ApplicationContext
    public Context context(){
        return context.getApplicationContext();
    }

    @Provides
    @VisiumApplicationScope
    Realm provideRealm(){
        return Realm.getDefaultInstance();
    }

    @Provides
    @VisiumApplicationScope
    RealmService provideRealmService(final Realm realm){
        return new RealmService(realm);
    }

}
