package com.example.arek.visium.screens.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.realm.ListOfCategories;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;
import com.example.arek.visium.rest.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arek on 9/27/2017.
 */

public class LoginManager {

    private static final String TAG = "HOME";
    Realm realm;
    private ApiInterface mApiInterface;
    private String token;
    private String mUsername;
    private String mPassword;
    private Context mContext;
    LoginActivity mLoginActivity;
    private final UserStorage userStorage;
    private UserLogin userLogin;
    private Call<String> loginCall;
    private Retrofit mRetrofit;
    private static final String LOGIN_FAILED = "Login failed";
    private Realm realm;


    public LoginManager(UserStorage userStorage, ApiInterface apiInterface, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.mApiInterface = apiInterface;
        this.mRetrofit = retrofit;
    }

    public void onAttach(LoginActivity loginActivity){
        this.mLoginActivity = loginActivity;
    }

    public void onStop(){
        this.mLoginActivity = null;
    }

    public void attemptLogin(String userName, String password){

        mLoginActivity.showProgressDialog();
        realm = Realm.getDefaultInstance();
        this.mUsername = userName;
        this.mPassword = password;
        userLogin = new UserLogin(mUsername, mPassword);
        Log.d(TAG, "Log in");

        if (loginCall == null){
            loginCall = mApiInterface.loginUser(userLogin);
            updateProgress();
            loginCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loginCall = null;
                    updateProgress();
                    if (response.isSuccessful()){
                            token = response.body().toString();
                            userStorage.login(userLogin, token);
                            createOrUpdateToken();
                        if (mLoginActivity!=null){
                            mLoginActivity.onLoginSuccess();
                        }else {
                            ResponseBody responseBody = response.errorBody();
                            Converter<ResponseBody, ErrorResponse> converter = mRetrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                            try {
                                ErrorResponse errorResponse = converter.convert(responseBody);
                                mLoginActivity.onLoginFailed(errorResponse.error);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        mLoginActivity.onLoginFailed(response.errorBody().toString());
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loginCall = null;
                    updateProgress();
                    mLoginActivity.onLoginFailed(LOGIN_FAILED);
                }
            });
        }
    }
    private void updateProgress() {
        if (mLoginActivity != null){
            mLoginActivity.showProgress(loginCall != null);
        }
    }
    public void createOrUpdateToken(){
        Token mToken = realm.where(Token.class).findFirst();
        realm.beginTransaction();
        if (mToken == null){
            mToken = realm.createObject(Token.class);
            mToken.setM_token(token);
        }else{
            mToken.setM_token(token);
            Log.d("my token: ", mToken.getM_token());
        }
        realm.commitTransaction();
    }

    public void checkSavedPreferences(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<ListOfCategories> listListOfCategories = realm.where(ListOfCategories.class).findAll();
        if (listListOfCategories != null){
            LoginActivity.userPreferencesStatus();
        }

    }

}
