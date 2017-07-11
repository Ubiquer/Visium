package com.example.arek.visium.rest;

/**
 * Created by Arek on 2017-06-25.
 */

public class ApiAdapter {

    private ApiAdapter() {}
//    "http://cleanidentity20170624060141.azurewebsites.net/
//    http://cleanidentity20170624060141.azurewebsites.net/"
//    "http://visiumapp.azurewebsites.net/"

    public static final String BASE_URL = "http://visiumapp.azurewebsites.net/";
//    public static final String BASE_URL = "https://requestb.in/";

    public static ApiInterface getAPIService() {

        return ServiceGenerator.getClient(BASE_URL).create(ApiInterface.class);
    }

//    public static ApiInterface getApiServiceWithOkHttp(){
//
//        return ServiceGenerator.postImage(BASE_URL).create(ApiInterface.class);
//
//    }

}
