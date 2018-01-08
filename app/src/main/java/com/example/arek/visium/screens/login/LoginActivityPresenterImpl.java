package com.example.arek.visium.screens.login;

import android.util.Patterns;

import com.example.arek.visium.model.UserLogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by arek on 9/27/2017.
 */

public class LoginActivityPresenterImpl implements LoginActivityPresenter, LoginRepository.OnLoginListener,
        LoginRepository.OnCheckSavedPreferences{

    private final LoginActivityView loginActivityView;
    private final LoginRepository loginRepository;

    @Inject
    public LoginActivityPresenterImpl(LoginActivityView loginActivityView, LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        this.loginActivityView = loginActivityView;
    }

    @Override
    public void onCreate(){
        loginRepository.checkSavedPreferences(this);
    }

    @Override
    public void onDestroy(){

    }

    @Override
    public void attemptLogin(UserLogin userLogin) {
        loginActivityView.showProgressDialog();
        loginRepository.logIn(userLogin, this);
    }

    @Override
    public void onLoginProgress(boolean notLoggedIn) {

        if (notLoggedIn){
            loginActivityView.showProgress(true);
        }else {
            loginActivityView.showProgress(false);
        }
    }

    @Override
    public void onLoginFinished(boolean loginSuccess, String loginMessage) {
        if (loginSuccess){
            loginActivityView.onLoginSuccess();
        }else
            loginActivityView.onLoginFailed(loginMessage);
    }

    @Override
    public void savedPreferencesStatus(boolean status) {
        loginActivityView.userPreferencesStatus(status);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void validateLoginData(String email, String password) {

        Pattern pattern;
        Matcher matcher;
        boolean passwordError = true;
        boolean emailError = true;
        UserLogin userLogin = new UserLogin();

        final String passwordValidationPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_+=-|<>?}~;])(?=\\S+$).{6,14}";
        pattern = Pattern.compile(passwordValidationPattern);
        matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            loginActivityView.setPasswordError(true);
        }else {
            passwordError = false;
            loginActivityView.setPasswordError(false);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginActivityView.setEmailError(true);
        } else {
            emailError = false;
            loginActivityView.setEmailError(false);
        }
        if (!passwordError && !emailError) {
            userLogin.setEmail(email);
            userLogin.setPassword(password);
            attemptLogin(userLogin);
        }
    }

}
