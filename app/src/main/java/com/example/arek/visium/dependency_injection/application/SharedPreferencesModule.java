package com.example.arek.visium.dependency_injection.storage;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arek on 12/5/2017.
 */
@Module
public class SharedPreferencesModule {

    @Provides
    @StorageScope
    SharedPreferences sharedPreferences(Context context){
        return context.getSharedPreferences("My_preferences", Context.MODE_PRIVATE);
    }

}
