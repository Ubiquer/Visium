package com.example.arek.visium.screens.login;

import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.screens.CredentialsValidator;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by arek on 9/27/2017.
 */

public class LoginActivityPresenterImpl implements LoginActivityPresenter, LoginRepository.OnLoginListener,
        LoginRepository.OnCheckSavedPreferences{

    private final LoginActivityView view;
    private final LoginRepository loginRepository;
    private final CredentialsValidator validator;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public LoginActivityPresenterImpl(LoginActivityView view, LoginRepository loginRepository, CredentialsValidator validator) {
        this.loginRepository = loginRepository;
        this.view = view;
        this.validator = validator;
    }

    @Override
    public void onCreate(){

        loginRepository.checkSavedPreferences(this);
        Observable<Boolean> emailObservable = view.emailObservable().map(isEmailValid()).observeOn(AndroidSchedulers.mainThread());
        Observable<Boolean> passwordObservable = view.passwordObservable().map(isPasswordValid()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(emailObservable.subscribe(updateEmailViewState()));
        compositeDisposable.add(passwordObservable.subscribe(updatePasswordViewState()));

        compositeDisposable.add(Observable.combineLatest(view.emailObservable(), view.passwordObservable(),
                (emailCharSequence, passwordCharSequence) -> {
                    String password = passwordCharSequence.toString();
                    return validator.validEmail(emailCharSequence) && !password.isEmpty() && validator.validPassword(passwordCharSequence);

                }).subscribe(fieldsFilledCorrectly -> {
            if (fieldsFilledCorrectly) {
                view.enableLoginButton(true);
            }
            else{
                view.enableLoginButton(false);
            }
        }));
    }

    @Override
    public void onDestroy(){
        compositeDisposable.clear();
    }

    @Override
    public void attemptLogin(String email, String password) {

        UserLogin userLogin = new UserLogin(email, password);
        loginRepository.logIn(userLogin, this);

    }

    @Override
    public Function<CharSequence, Boolean> isEmailValid() {
        return emailTextAfterTextChangeEvent -> validator.validEmail(emailTextAfterTextChangeEvent);
    }

    @Override
    public Function<CharSequence, Boolean> isPasswordValid() {
        return passwordTextAfterTextChangeEvent -> validator.validPassword(passwordTextAfterTextChangeEvent);
    }

    @Override
    public Consumer<Boolean> updateEmailViewState() {
        return isValid -> {
            if (!isValid){
                view.onEmailNotValid();
            } else
                view.onEmailValid();
        };
    }

    @Override
    public Consumer<Boolean> updatePasswordViewState() {
        return isValid -> {
            if (!isValid){
                view.onPasswordNotValid();
            }else
                view.onPasswordValid();
        };
    }


    @Override
    public void onLoginProgress(boolean notLoggedIn) {

        if (notLoggedIn){
            view.showProgress(true);
        }else {
            view.showProgress(false);
        }
    }
    @Override
    public void onLoginFinished(boolean loginSuccess, String loginMessage) {
        if (loginSuccess){
            view.onLoginSuccess();
        }else
            view.onLoginFailed(loginMessage);
    }

    @Override
    public void savedPreferencesStatus(boolean status) {
        view.userPreferencesStatus(status);
    }

    @Override
    public void showProgressDialog() {

    }

}
