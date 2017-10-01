package com.example.arek.visium.screens.register;

import android.widget.Toast;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.model.RegisterRequest;
import com.example.arek.visium.rest.ApiInterface;
import com.example.arek.visium.rest.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arek on 9/30/2017.
 */

public class RegisterManager {

    private RegisterActivity mRegisterActivity;
    private final UserStorage mUserStorage;
    private final ApiInterface mApiInterface;
    private Call<String> userResponseCall;
    private final Retrofit mRetrofit;

    public RegisterManager(UserStorage userStorage, ApiInterface apiInterface, Retrofit retrofit) {
        this.mUserStorage = userStorage;
        this.mApiInterface = apiInterface;
        this.mRetrofit = retrofit;
    }

    public void onAttach(RegisterActivity registerActivity){
        this.mRegisterActivity = registerActivity;
    }

    public void onStop(){
        this.mRegisterActivity = null;
    }

    public void register(String email, String password){

        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setmEmail(email);
        registerRequest.setmPassword(password);

        if (userResponseCall == null){
            userResponseCall = mApiInterface.registerUser(registerRequest);
            userResponseCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        if (mRegisterActivity != null){
                            mRegisterActivity.onSignUpSuccess();
                        }
                        else{
                            ResponseBody responseBody = response.errorBody();
                            Converter<ResponseBody, ErrorResponse> converter = mRetrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                            try {
                                ErrorResponse errorResponse = converter.convert(responseBody);
                                Toast.makeText(mRegisterActivity, "Error: " + errorResponse, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

    }

}
