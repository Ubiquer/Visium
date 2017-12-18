package com.example.arek.visium.screens.login;

/**
 * Created by arek on 7/25/2017.
 */

public interface LoginActivityView {

    void onLoginFailed(String errorBody);
    void onLoginSuccess();
    void login();
    void showProgress(boolean b);
    void showProgressDialog();
    void setEmailError(boolean errorS);
    void setPasswordError(boolean errorStatus);
    void userPreferencesStatus(boolean status);

}
