package com.example.arek.visium.dependency_injection.screens.login;

import android.content.SharedPreferences;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.dependency_injection.application.VisiumApplicationScope;

import dagger.Module;
import dagger.Provides;

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

}
