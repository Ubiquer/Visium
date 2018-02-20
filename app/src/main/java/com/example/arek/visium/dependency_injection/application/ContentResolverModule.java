package com.example.arek.visium.dependency_injection.application;

import android.content.ContentResolver;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arek on 2/17/2018.
 */
@Module(includes = ApplicationModule.class)
public class ContentResolverModule {

    @Provides
    @VisiumApplicationScope
    ContentResolver resolver(@ApplicationContext Context context){
        return context.getContentResolver();
    }

}
