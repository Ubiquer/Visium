package com.example.arek.visium.rest;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Arek on 2017-06-28.
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
                if(header.startsWith("XSRF-TOKEN")) {
                    String newCookie[]=header.split(";");
                    System.out.println("newCookie Length: "+newCookie.length);
                    for(String ss:newCookie) {
                        if(ss.startsWith("XSRF-TOKEN")) {
                            System.out.println("Cookies ss: " + ss);
//                            sharedPrefs.setToken(ss);
                        }
                    }
                }
            }
        }
        return originalResponse;
    }
}

