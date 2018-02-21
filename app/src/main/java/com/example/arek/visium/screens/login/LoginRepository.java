package com.example.arek.visium.screens.login;

import com.example.arek.visium.dao.UserLogin;

/**
 * Created by arek on 11/16/2017.
 */

public interface LoginRepository {

    void logIn(UserLogin userLogin, OnLoginListener onLoginListener);

//    void createOrUpdateToken(String token);

    void checkSavedPreferences(OnCheckSavedPreferences onCheckSavedPreferences);

    interface OnLoginListener {
        void onLoginFinished(boolean loginStatus, String loginMessage);
        void onLoginProgress(boolean progress);
        void showProgressDialog();
    }

    interface OnCheckSavedPreferences{
        void savedPreferencesStatus(boolean status);
    }

    void deleteToken();
    void closeRealm();

}
