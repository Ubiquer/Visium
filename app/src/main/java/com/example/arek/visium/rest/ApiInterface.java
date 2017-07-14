package com.example.arek.visium.rest;

import android.widget.ImageView;

import com.example.arek.visium.model.TokenAuth;
import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.model.UserRegistration;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Arek on 2017-06-25.
 */

public interface ApiInterface {

//    "api/Account/Authenticate"
//    /1m5n0xt1/
//    /api/User/AddImage
//    /17pdghz1

    @Headers("Content-Type:application/json")
    @POST("/api/Account/register")
    Call<String> registerUser(@Body UserRegistration userRegistration);

    @Headers("Content-Type:application/json")
    @POST("api/Account/Authenticate")
    Call<String> loginUser(@Body UserLogin userLogin);

//    @Headers("Content-Type:application/json")
    @GET("/api/Account/ValidateToken")
    Call<String> validateToken(@Header("Authorization") String authToken);

    @Multipart
    @POST("api/Upload/PostUserImage")
    Call<ResponseBody> uploadImage(@Part("description") RequestBody description,
                             @Part MultipartBody.Part photo);

//    Call<ServerRes>
//    Observable<Example> getExample();
}
