package com.example.arek.visium.rest;

import com.example.arek.visium.model.ItunesResult;
import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.model.UserPreferencesWithImage;
import com.example.arek.visium.model.UserRegistration;

import java.util.List;
import java.util.Map;

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
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Arek on 2017-06-25.
 */

public interface ApiInterface {

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
    @POST(IntentKeys.UPLOAD_IMAGE)
    Call<ResponseBody> uploadImage(@Header("Authorization") String token, @Query("category") String category,
                                   @Part MultipartBody.Part photo);

    @GET(IntentKeys.GET_ALL_CATEGORIES)
    Call<List<UserPreferencesWithImage>> getUserPreferences();

    @GET("/lookup")
    Call<ItunesResult> loadSongs(@QueryMap Map<String, String> options);

}
