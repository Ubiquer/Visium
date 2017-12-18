package com.example.arek.visium.screens.register;

/**
 * Created by arek on 11/28/2017.
 */

public interface RegisterRepository {

    void register(String email, String password, OnSignUpListener onSignUpListener);

    interface OnSignUpListener {
        void onSignUpFinishedSuccess(String response);
        void onSignUpFinishedFailure(String response);
        void onResponseUnsuccessful(String response);
    }

}
