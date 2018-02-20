package com.example.arek.visium.dependency_injection.network;

import android.content.Context;

import com.example.arek.visium.dependency_injection.application.ApplicationContext;
import com.example.arek.visium.dependency_injection.application.ApplicationModule;
import com.example.arek.visium.dependency_injection.application.VisiumApplicationScope;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by arek on 12/4/2017.
 */
@Module(includes = ApplicationModule.class)
public class NetworkModule {

    @Provides
    @VisiumApplicationScope
    Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000);
    }

    @Provides
    @VisiumApplicationScope
    File cacheFile(@ApplicationContext Context context){
        return new File(context.getCacheDir(), "okhttp_cach");
    }

    @Provides
    @VisiumApplicationScope
    public HttpLoggingInterceptor loggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {

        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @VisiumApplicationScope
    OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
       return new OkHttpClient.Builder()
               .addInterceptor(loggingInterceptor)
               .cache(cache)
               .build();
    }

}
