package com.example.arek.visium;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.model.UserRegistration;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 7/25/2017.
 */

class LoginActivityPresenter {

    private LoginActivityView view;
    private static final String TAG = "HOME";
    Context context;
    Realm realm;
    private UserRegistration userRegistration;
    private ProgressDialog progressDialog;
    private ApiInterface mApiInterface;
    private String token;
    private String mUsername;
    private String mPassword;

    public LoginActivityPresenter(LoginActivityView loginView){
        this.view = loginView;
    }

    public void attemptLogin(String userName, String password){

        mApiInterface = ApiAdapter.getAPIService();
        realm = Realm.getDefaultInstance();
        this.mUsername = userName;
        this.mPassword = password;

        Log.d(TAG, "Log in");
        UserLogin userLogin = new UserLogin(mUsername, mPassword);
        mApiInterface.loginUser(userLogin).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
//                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    token = response.body().toString();
                    createOrUpdateToken();
                    view.onLoginSuccess();

                }else {
//                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this, "Incorrect login ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.onLoginFailed();
//                Toast.makeText(LoginActivity.this, "error: ", Toast.LENGTH_SHORT).show();
            }
        });
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
