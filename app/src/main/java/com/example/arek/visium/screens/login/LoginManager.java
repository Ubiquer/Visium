package com.example.arek.visium.screens.login;

import android.content.Context;
import android.util.Log;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 9/27/2017.
 */

public class UserManager {

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
    Call<String> loginCall;

    public UserManager(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void onAttach(LoginActivity loginActivity){
        this.mLoginActivity = loginActivity;
    }

    public void onStop(){
        this.mLoginActivity = null;
    }

    public void attemptLogin(String userName, String password){


        mLoginActivity.showProgressDialog();

        mApiInterface = ApiAdapter.getAPIService();
        realm = Realm.getDefaultInstance();
        this.mUsername = userName;
        this.mPassword = password;
        userLogin = new UserLogin(mUsername, mPassword);
        Log.d(TAG, "Log in");

        if (loginCall ==null){
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
                        if (mLoginActivity != null){
                            mLoginActivity.onLoginSuccess();
                        }
//                    ResponseBody responseBody = response.errorBody();
//                    try {
//
//                        Toast.makeText(mContext, "error: " + responseBody.string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    }else {
                        mLoginActivity.onLoginFailed();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loginCall = null;
                    updateProgress();
                    mLoginActivity.onLoginFailed();
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

}
