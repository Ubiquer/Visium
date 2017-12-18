package com.example.arek.visium.screens.login;

import android.util.Patterns;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.rest.VisiumService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by arek on 9/27/2017.
 */

public class LoginActivityPresenter implements LoginRepository.OnLoginListener,
        LoginRepositoryImpl.OnCheckSavedPreferences{

    private LoginActivityView loginActivityView;
    private final LoginRepository loginRepository;

    @Inject
    public LoginActivityPresenter(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void onAttach(LoginActivityView loginActivityView){
        this.loginActivityView = loginActivityView;
        loginRepository.checkSavedPreferences(this);
    }

    public void onDetach(){
        this.loginActivityView = null;
//        this.loginRepository = null;
    }

    public void attemptLogin(String userName, String password) {
        loginActivityView.showProgressDialog();
        loginRepository.loginToApi(userName, password, this);
    }

    @Override
    public void onLoginProgress(boolean status) {

        if (status){
            loginActivityView.showProgress(true);
        }else {
            loginActivityView.showProgress(false);
        }
    }

    @Override
    public void onFinished(boolean loginStatus, String loginMessage) {
        if (loginStatus){
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

    public void validateLoginData(String email, String password) {

        Pattern pattern;
        Matcher matcher;
        boolean hasErrors = false;

        final String passwordValidationPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_+=-|<>?}~;])(?=\\S+$).{6,14}";
        pattern = Pattern.compile(passwordValidationPattern);
        matcher = pattern.matcher(password);

        if (matcher.matches() != true) {
            loginActivityView.setPasswordError(true);
            hasErrors = true;
        }else {
            loginActivityView.setPasswordError(false);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginActivityView.setEmailError(true);
            hasErrors = true;
        } else {
            loginActivityView.setEmailError(false);
        }
        if (!hasErrors) {
            attemptLogin(email, password);
        }
    }
}
