package com.example.arek.visium.screens.login;

/**
 * Created by arek on 7/25/2017.
 */

public interface LoginActivityView {

    void onLoginFailed(String errorBody);
    void onLoginSuccess();
//    void validate();
    void login();
    boolean validate();
    void showProgress(boolean b);
}
