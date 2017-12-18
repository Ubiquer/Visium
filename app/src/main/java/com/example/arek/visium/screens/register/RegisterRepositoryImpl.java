package com.example.arek.visium.screens.register;

import android.content.Context;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.model.RegisterRequest;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.rest.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arek on 11/28/2017.
 */

public class RegisterRepositoryImpl implements RegisterRepository {

    private final UserStorage userStorage;
    private final VisiumService visiumService;
    private Call<String> userResponseCall;
//    private Retrofit retrofit;
//    private Context context;

    @Inject
    public RegisterRepositoryImpl(UserStorage userStorage, VisiumService visiumService) {
        this.userStorage = userStorage;
        this.visiumService = visiumService;
    }

    @Override
    public void register(String email, String password, OnSignUpListener onSignUpListener) {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setmEmail(email);
        registerRequest.setmPassword(password);

//        context = VisiumApplication.getContext();
//        visiumService = ((VisiumApplication) context).getVisiumService();

        if (userResponseCall == null) {
            userResponseCall = visiumService.registerUser(registerRequest);
            userResponseCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {

                        onSignUpListener.onSignUpFinishedSuccess(response.message());

                    } else {
                        ResponseBody responseBody = response.errorBody();
//                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        //                            ErrorResponse errorResponse = converter.convert(responseBody);
                        onSignUpListener.onResponseUnsuccessful(String.valueOf(responseBody));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    onSignUpListener.onSignUpFinishedFailure(t.getMessage());

                }
            });
        }

    }
}

