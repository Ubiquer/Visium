package com.example.arek.visium.screens.register;

import com.example.arek.visium.screens.CredentialsValidator;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by arek on 9/30/2017.
 */

public class RegisterActivityPresenterImpl implements RegisterActivityPresenter, RegisterRepository.OnSignUpListener {


    private final RegisterRepository repository;
    private final RegisterActivityView view;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final CredentialsValidator validator;

    @Inject
    public RegisterActivityPresenterImpl(RegisterActivityView view, RegisterRepository repository, CredentialsValidator validator) {
        this.repository = repository;
        this.view = view;
        this.validator = validator;
    }

    @Override
    public void onCreate() {

        Observable<Boolean> emailObservable = view.emailObservable().map(isEmailValid());
        Observable<Boolean> passwordObservable = view.passwordObservable().map(isPasswordValid());
        Observable<Boolean> confirmPasswordObservable = view.confirmPasswordObservable().map(isConfirmPasswordValid());

        compositeDisposable.add(emailObservable.subscribe(updateEmailViewState()));
        compositeDisposable.add(passwordObservable.subscribe(updatePasswordViewState()));
        compositeDisposable.add(confirmPasswordObservable.subscribe(updateConfirmPasswordViewState()));

        compositeDisposable.add(Observable.combineLatest(view.passwordObservable(), view.confirmPasswordObservable(),
                (passwordCharSequence, confirmPasswordCharSequence) -> {
            String password = passwordCharSequence.toString();
            String confirmPassword = confirmPasswordCharSequence.toString();
            return !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword);

        }).subscribe(fieldsMatch -> {
            if (fieldsMatch) {
                view.onPasswordsMatch();
            }
            else{
                view.onPasswordsDiffer();
            }
        }));

        compositeDisposable.add(Observable.combineLatest(view.emailObservable(), view.passwordObservable(), view.confirmPasswordObservable(),
                (emailCharSequence, passwordCharSequence, confirmPasswordCharSequence) -> {
                    String password = passwordCharSequence.toString();
                    String confirmPassword = confirmPasswordCharSequence.toString();

                    return validator.validEmail(emailCharSequence) && !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword);

                }).subscribe(fieldsFilledCorrectly -> {
            if (fieldsFilledCorrectly) {
                view.enableSignUpButton(true);
            }
            else{
                view.enableSignUpButton(false);
            }
        }));

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onRegister(String email, String password){
        repository.register(email, password, this);
   }

    @Override
    public Function<CharSequence, Boolean> isPasswordValid() {
        return  passwordTextAfterChangeEvent -> validator.validPassword(passwordTextAfterChangeEvent);
    }

    @Override
    public Function<CharSequence, Boolean> isConfirmPasswordValid() {
        return confirmPasswordTextAfterChangeEvent -> validator.validPassword(confirmPasswordTextAfterChangeEvent);
    }

    @Override
    public Function<CharSequence, Boolean> isEmailValid() {
        return emailTextAfterTextChangeEvent -> validator.validEmail(emailTextAfterTextChangeEvent);
    }

    @Override
    public Consumer<Boolean> updatePasswordViewState() {
        return isValid -> {
            if (!isValid){
                view.onPasswordNotValid();
            }
        };
    }

    @Override
    public Consumer<Boolean> updateConfirmPasswordViewState() {
        return isValid ->{
            if (!isValid){
                view.onPasswordsDiffer();
            }
        };
    }

    @Override
    public Consumer<Boolean> updateEmailViewState() {
        return isValid ->{
            if (!isValid){
                view.onEmailNotValid();
            }
        };
    }

    @Override
    public void onSignUpFinishedSuccess(String response) {

        view.onSignUpSuccess();

    }

    @Override
    public void onSignUpFinishedFailure(String response) {

        view.onSingUpFailed(response);

    }

    @Override
    public void onResponseUnsuccessful(String response) {


    }
}
