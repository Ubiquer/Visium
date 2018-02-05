package com.example.arek.visium.screens.login;

import android.util.Log;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.UserStorage;
import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.realm.UserPreferencesCategories;
import com.example.arek.visium.rest.VisiumService;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 11/16/2017.
 */

public class LoginRepositoryImpl implements LoginRepository{

    private Call<String> loginCall;
    private final Realm realm;
    private String token;
    private final VisiumService visiumService;
    private final UserStorage userStorage;
    private final RealmService realmService;


    @Inject
    public LoginRepositoryImpl(RealmService realmService, UserStorage userStorage, VisiumService visiumService, Realm realm) {
        this.realmService = realmService;
        this.userStorage = userStorage;
        this.realm = realm;
        this.visiumService = visiumService;
    }

    @Override
    public void logIn(UserLogin userLogin, OnLoginListener onLoginListener) {

        if (loginCall == null){
            loginCall = visiumService.loginUser(userLogin);
            onLoginListener.onLoginProgress(false);
            loginCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loginCall = null;
                    onLoginListener.onLoginProgress(true);
                    if (response.isSuccessful()){
                        token = response.body();
                        if (token.length()<28){
                            onLoginListener.onLoginFinished(false, ("Account doesn't exist"));
                        }else {
                            userStorage.login(userLogin, token);
                            realmService.createOrUpdateToken(token);
                            onLoginListener.onLoginFinished(true, ("success"));
                        }
                    }else {
                        onLoginListener.onLoginFinished(false, response.errorBody().toString());
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loginCall = null;
                    onLoginListener.onLoginProgress(false);
                    onLoginListener.onLoginFinished(false, "login failed");
                }
            });
        }
    }
//    @Override
//    public void createOrUpdateToken(String token) {
//
//        Token mToken = realm.where(Token.class).findFirst();
//        realm.beginTransaction();
//        if (mToken == null){
//            mToken = realm.createObject(Token.class);
//            mToken.setM_token(token);
//        }else{
//            mToken.setM_token(token);
//            Log.d("my token: ", mToken.getM_token());
//        }
//        realm.commitTransaction();
//    }

    @Override
    public void checkSavedPreferences(OnCheckSavedPreferences onCheckSavedPreferences) {

        if (realmService.checkSavedPreferences()){
            onCheckSavedPreferences.savedPreferencesStatus(true);
        }else {
            onCheckSavedPreferences.savedPreferencesStatus(false);
        }
//        realm.beginTransaction();
//        List<UserPreferencesCategories> listOfCategories = realm.where(UserPreferencesCategories.class).findAll();
//        if (listOfCategories.size() != 0){
//            onCheckSavedPreferences.savedPreferencesStatus(true);
//        }else {
//            onCheckSavedPreferences.savedPreferencesStatus(false);
//        }
//        realm.commitTransaction();
    }

    @Override
    public void deleteToken() {
        realmService.deleteToken();
    }


}
