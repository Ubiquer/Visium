package com.example.arek.visium.screens.register;

/**
 * Created by arek on 7/26/2017.
 */

public interface SignUpActivityView {

    void signUp();
    void onSingUpFailed();
    void passwordsMatch();
    void passwordsDiffer();
    void invalidEmail();
    void validEmail();
    void incorrectPassword();
    void onSignUpSuccess();

}
