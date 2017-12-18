package com.example.arek.visium.screens.register;

import com.example.arek.visium.UserStorage;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by arek on 9/30/2017.
 */

public class RegisterActivityPresenter implements RegisterRepository.OnSignUpListener {


    private final RegisterRepository repository;
    private RegisterActivityView registerActivityView;

    @Inject
    public RegisterActivityPresenter(RegisterRepository repository) {
        this.repository = repository;
    }

    public void onAttach(RegisterActivityView registerActivityView){
        this.registerActivityView = registerActivityView;
    }

    public void onStop(){
        this.registerActivityView = null;
    }

    public void register(String email, String password){

        repository.register(email, password, this);
   }

    @Override
    public void onSignUpFinishedSuccess(String response) {

        registerActivityView.onSignUpSuccess();

    }

    @Override
    public void onSignUpFinishedFailure(String response) {

        registerActivityView.onSingUpFailed(response);

    }

    @Override
    public void onResponseUnsuccessful(String response) {



    }
}
