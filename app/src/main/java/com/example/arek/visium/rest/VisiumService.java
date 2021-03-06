package com.example.arek.visium.rest;

import com.example.arek.visium.dao.DuelImage;
import com.example.arek.visium.dao.Photographer;
import com.example.arek.visium.dao.RankingImageByCategory;
import com.example.arek.visium.dao.RegisterRequest;
import com.example.arek.visium.dao.UserLogin;
import com.example.arek.visium.dao.UserPreferencesWithImage;

import java.util.List;

import okhttp3.MultipartBody;
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

/**
 * Created by Arek on 2017-06-25.
 */

public interface VisiumService {

    @Headers("Content-Type:application/json")
    @POST("api/Account/Register")
    Call<String> registerUser(@Body RegisterRequest registerRequest);

    @Headers("Content-Type:application/json")
    @POST("api/Account/Authenticate")
    Call<String> loginUser(@Body UserLogin userLogin);

    @GET("/api/Account/ValidateToken")
    Call<String> validateToken(@Header("Authorization") String authToken);

    @Multipart
    @POST("api/User/UploadImage")
    Call<ResponseBody> uploadImage(@Header("Authorization") String token, @Query("category") String category,
                                   @Part MultipartBody.Part photo);
    @GET(ApiKeys.GET_ALL_CATEGORIES)
    Call<List<UserPreferencesWithImage>> getUserPreferences();

    @Headers("Content-Type:application/json")
    @POST("/api/User/AddCategories")
    Call<String> sendPreferences(@Body List<Integer> userPreferencesSelectedList);

    @GET("/api/Basic/GetTwoImagesFromCategory")
    Call<List<DuelImage>> getDuelImages(@Query("category") String category);

    @Headers("Content-Type:application/json")
    @POST("api/Basic/AddResultOfImagesBattle")
    Call<ResponseBody> postDuelResult(@Query("looserPictureId") int looserPictureId, @Query("winnerPictureId") int winnerPictureId);

    @Headers("Content-Type:application/json")
    @GET("api/Basic/GetImagesRanking")
    Call<List<RankingImageByCategory>> getAllImagesFromCategoryByRanking();

    @Headers("Content-Type:application/json")
    @GET("api/Basic/GetUsersRanking")
    Call<List<Photographer>> getAllPhotographers();

}
