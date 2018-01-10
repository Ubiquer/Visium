package com.example.arek.visium.screens.register;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.jakewharton.rxbinding2.InitialValueObservable;

import io.reactivex.Observable;

/**
 * Created by arek on 7/26/2017.
 */

public interface RegisterActivityView {

    void signUp();
    void onSingUpFailed(String message);
    Observable<CharSequence> passwordObservable();
    Observable<CharSequence> confirmPasswordObservable();
    Observable<CharSequence> emailObservable();
    void onPasswordsMatch();
    void onPasswordsDiffer();
    void onPasswordNotValid();
    void onSignUpSuccess();
    void onEmailNotValid();
    void enableSignUpButton(boolean enable);
}
