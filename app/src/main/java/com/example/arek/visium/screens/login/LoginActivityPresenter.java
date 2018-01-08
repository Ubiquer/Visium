package com.example.arek.visium.screens.login;

import com.example.arek.visium.model.UserLogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscription;

/**
 * Created by arek on 12/13/2017.
 */

public interface LoginActivityPresenter {

    void onCreate();
    void onDestroy();
    void attemptLogin(UserLogin userLogin);
    void validateLoginData(String email, String password);


}
