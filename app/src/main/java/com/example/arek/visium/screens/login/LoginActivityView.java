package com.example.arek.visium.screens.login;

import io.reactivex.Observable;

/**
 * Created by arek on 7/25/2017.
 */

public interface LoginActivityView {

    void onLoginFailed(String errorBody);
    void onLoginSuccess();
    void login();
    void showProgress(boolean b);
    void showProgressDialog();
    Observable<CharSequence> emailObservable();
    Observable<CharSequence> passwordObservable();
    void onEmailNotValid();
    void onEmailValid();
    void onPasswordNotValid();
    void userPreferencesStatus(boolean status);
    void onPasswordValid();
    void enableLoginButton(boolean b);
}
