package com.example.arek.visium.screens.login;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by arek on 12/13/2017.
 */

public interface LoginActivityPresenter {

    void onCreate();
    void onDestroy();
    void attemptLogin(String email, String password);
    Function<CharSequence, Boolean> isEmailValid();
    Function<CharSequence, Boolean> isPasswordValid();
    Consumer<Boolean> updateEmailViewState();
    Consumer<Boolean> updatePasswordViewState();
    void deleteLoginToken();
}
