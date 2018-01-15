package com.example.arek.visium.screens.register;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by arek on 12/19/2017.
 */

public interface RegisterActivityPresenter {

    void onCreate();
    void onDestroy();
    void onRegister(String email, String password);
    Function<CharSequence, Boolean> isPasswordValid();
    Function<CharSequence, Boolean> isConfirmPasswordValid();
    Function<CharSequence, Boolean> isEmailValid();
    Consumer<Boolean> updatePasswordViewState();
    Consumer<Boolean> updateConfirmPasswordViewState();
    Consumer<Boolean> updateEmailViewState();

}
