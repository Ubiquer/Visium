package com.example.arek.visium.dependency_injection.application;

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
    @VisiumApplicationScope
    SharedPreferences sharedPreferences(@ApplicationContext Context context){
        return context.getSharedPreferences("My_preferences", Context.MODE_PRIVATE);
    }

}
