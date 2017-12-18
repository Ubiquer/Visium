package com.example.arek.visium.dependency_injection.network;

import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.rest.VisiumService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arek on 12/5/2017.
 */
@Module(includes = NetworkModule.class)
public class VisiumServiceModule {

    @Provides
    public VisiumService getVisiumService(Retrofit visiumRetrofit){

        return visiumRetrofit.create(VisiumService.class);

    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson){

        Retrofit visiumRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("http://visiumapp.azurewebsites.net/")
                .build();

        return visiumRetrofit;
    }


}
