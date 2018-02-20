package com.example.arek.visium.dependency_injection.application;

import android.content.SharedPreferences;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.UserStorage;
import com.example.arek.visium.dependency_injection.application.VisiumApplicationScope;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by arek on 12/5/2017.
 */
@Module
public class UserStorageModule {

    @Provides
    @VisiumApplicationScope
    UserStorage userStorage(SharedPreferences sharedPreferences){
        UserStorage userStorage = new UserStorage(sharedPreferences);
        return userStorage;
    }

//    @Provides
//    @VisiumApplicationScope
//    Realm provideRealm(){
//        return Realm.getDefaultInstance();
//    }
//
//    @Provides
//    @VisiumApplicationScope
//    RealmService realmService(final Realm realm){
//        return new RealmService(realm);
//    }

}
