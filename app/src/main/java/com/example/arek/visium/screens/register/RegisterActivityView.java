package com.example.arek.visium.screens.register;

/**
 * Created by arek on 7/26/2017.
 */

public interface RegisterActivityView {

    void signUp();
    void onSingUpFailed(String message);
    void onPasswordsMatch();
    void onPasswordsDiffer();
    void onEmailsMatch();
    void onEmailsDiffer();
    void onSignUpSuccess();

}
