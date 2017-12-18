package com.example.arek.visium.dependency_injection.application;

import android.content.Context;

import com.example.arek.visium.dependency_injection.network.NetworkModule;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by arek on 12/5/2017.
 */

@Module(includes = {ContextModule.class, NetworkModule.class})
public class PicassoModule {

    @Provides
    @VisiumApplicationScope
    public Picasso picasso(@ApplicationContext Context context, OkHttp3Downloader okHttp3Downloader){
        return new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build();
    }

    @Provides
    @VisiumApplicationScope
    OkHttp3Downloader okHttpDownloader(OkHttpClient okHttpClient){
        return new OkHttp3Downloader(okHttpClient);
    }

}
