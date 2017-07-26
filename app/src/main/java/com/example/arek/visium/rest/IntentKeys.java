package com.example.arek.visium.rest;

/**
 * Created by arek on 7/19/2017.
 */

public class IntentKeys {

//    BASE_URL
    public static final String BASE_URL = "http://visiumapp.azurewebsites.net/";

//   USER ACCOUNT ENDPOINTS
    public static final String GET_TOKEN = "api/Account/Authenticate";
    public static final String CREATE_USER = "api/Account/Register";

    public static final String UPLOAD_IMAGE = "api/User/UploadImage";
    public static final String GET_ALL_CATEGORIES = "api/Basic/GetAllCategories";
    public static final String GET_ALL_PICTURES = "api/User/GetAllPictures";

}
