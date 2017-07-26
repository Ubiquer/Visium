package com.example.arek.visium.rest;

import com.example.arek.visium.rest.Services.ServiceGenerator;

/**
 * Created by Arek on 2017-06-25.
 */

public class ApiAdapter {

    private ApiAdapter() {}

    public static ApiInterface getAPIService() {

        return ServiceGenerator.getClient(IntentKeys.BASE_URL).create(ApiInterface.class);
    }

}
