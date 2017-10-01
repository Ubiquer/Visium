package com.example.arek.visium;

import android.content.SharedPreferences;

import com.example.arek.visium.model.UserLogin;


/**
 * Created by arek on 9/27/2017.
 */

public class UserStorage {


    public static final String SESSION_TOKEN = "sessionToken";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private final SharedPreferences mSharedPreferences;

    public UserStorage(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    public void login(UserLogin userLogin, String token) {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(SESSION_TOKEN, token);
        editor.putString(EMAIL, userLogin.getmEmail());
        editor.putString(PASSWORD, userLogin.getmPassword());
        editor.apply();

    }

    public boolean hasToLogin() {
        return mSharedPreferences.getString(SESSION_TOKEN, "").isEmpty();
    }

    public void logout(){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();

    }

}
