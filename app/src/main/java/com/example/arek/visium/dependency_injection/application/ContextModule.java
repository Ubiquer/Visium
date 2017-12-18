package com.example.arek.visium.dependency_injection.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arek on 12/4/2017.
 */
@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @VisiumApplicationScope
    @ApplicationContext
    public Context context(){
        return context.getApplicationContext();
    }

}
