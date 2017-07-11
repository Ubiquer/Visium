package com.example.arek.visium.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Arek on 2017-06-28.
 */

public class SessionRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder request = original.newBuilder();

//        request.header("Cookie",ServiceSharedPrefs.getInstance().getToken()));

        request.method(original.method(), original.body());

        return chain.proceed(request.build());
    }

}