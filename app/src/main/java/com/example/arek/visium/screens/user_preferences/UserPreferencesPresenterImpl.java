package com.example.arek.visium.screens.user_preferences;


import com.example.arek.visium.model.UserPreferencesWithImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by arek on 11/4/2017.
 */

public class UserPreferencesPresenterImpl implements UserPreferencesPresenter, UserPreferencesRepository.OnDownLoadFinishedListener{

    private final UserPreferencesView view;
    private final UserPreferencesRepository repository;

    @Inject
    public UserPreferencesPresenterImpl(UserPreferencesView view, UserPreferencesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onCreate() {
       repository.loadPreferenceModels(this);
    }

    @Override
    public void onDestroy() {
        repository.onDestroy();
    }

    @Override
    public void sendPreferencesToDB(ArrayList<Integer> chosenPreferences){
        repository.commitPreferencesToDB(chosenPreferences);
    }

    @Override
    public void commitSelectedPreferencesToRealm(List selectedPreferences){
        repository.commitSelectedCategoriesToRealm(selectedPreferences);
    }

   @Override
    public void onLoadPreferences(ArrayList<UserPreferencesWithImage> allPreferences) {
        if (view != null){
            view.showData(allPreferences);
        }
    }

    @Override
    public void onLoadFailed(String message) {
        view.onPreferencesDownloadFailed(message);
    }

    @Override
    public void onLoadResponseFailure(String message) {
        view.onResponseFailure(message);
    }

}
