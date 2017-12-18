package com.example.arek.visium;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.arek.visium.model.UserLogin;


/**
 * Created by arek on 9/27/2017.
 */

public class UserStorage {

    public static final String SESSION_TOKEN = "sessionToken";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private final SharedPreferences sharedPreferences;

    public UserStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void login(UserLogin userLogin, String token) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_TOKEN, token);
        editor.putString(EMAIL, userLogin.getEmail());
        editor.putString(PASSWORD, userLogin.getPassword());
        editor.apply();
    }

    public boolean noSessionToken() {
        Log.d("token", sharedPreferences.getString(SESSION_TOKEN, ""));
        String myToken = sharedPreferences.getString(SESSION_TOKEN, "");
        Log.d("token", myToken);
        return myToken.isEmpty();
    }

    public void logout(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

    }

    public String getFullName() {
        return sharedPreferences.getString(EMAIL,"");
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL,"");
    }
}
