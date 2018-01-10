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
    private final CompositeDisposable compositeDisposable;
    private final CredentialsValidator validator;

    @Inject
    public RegisterActivityPresenterImpl(RegisterActivityView view, RegisterRepository repository, CredentialsValidator validator, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.view = view;
        this.validator = validator;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onCreate() {

        Observable<Boolean> passwordObservable = view.passwordObservable().map(isPasswordValid());
        Observable<Boolean> confirmPasswordObservable = view.confirmPasswordObservable().map(isConfirmPasswordValid());
        Observable<Boolean> emailPasswordObservable = view.emailObservable().map(isEmailValid());

        compositeDisposable.add(passwordObservable.subscribe(updatePasswordViewState()));
        compositeDisposable.add(confirmPasswordObservable.subscribe(updateConfirmPasswordViewState()));
        compositeDisposable.add(emailPasswordObservable.subscribe(updateEmailViewState()));

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

                    return validator.isEmailValid(emailCharSequence) && !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword);

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
        compositeDisposable.dispose();
    }

    @Override
    public void onRegister(String email, String password){
        repository.register(email, password, this);
   }

    @Override
    public Function<CharSequence, Boolean> isPasswordValid() {
        return  passwordTextAfterChangeEvent -> validator.isPasswordValid(passwordTextAfterChangeEvent);
    }

    @Override
    public Function<CharSequence, Boolean> isConfirmPasswordValid() {
        return confirmPasswordTextAfterChangeEvent -> validator.isPasswordValid(confirmPasswordTextAfterChangeEvent);
    }

    @Override
    public Function<CharSequence, Boolean> isEmailValid() {
        return emailTextAfterTextChangeEvent -> validator.isEmailValid(emailTextAfterTextChangeEvent);
    }

    @Override
    public Function<CharSequence, Boolean> isUserNameValid() {
        return null;
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
