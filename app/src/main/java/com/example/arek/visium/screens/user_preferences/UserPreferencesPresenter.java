package com.example.arek.visium.screens.user_preferences;

import com.example.arek.visium.model.UserPreferencesWithImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 11/4/2017.
 */

public class UserPreferencesPresenter implements UserPreferencesRepository.OnDownLoadFinishedListener{

    private UserPreferencesView userPreferencesView;
    private UserPreferencesRepositoryImpl userPreferencesRepository;

    public UserPreferencesPresenter() {
    }

    public void onAttach(UserPreferencesView userPreferencesView){
        this.userPreferencesView = userPreferencesView;
        userPreferencesRepository = new UserPreferencesRepositoryImpl();
        userPreferencesRepository.loadPreferenceModels(this);
    }

    public void onDetach(){
        userPreferencesView = null;
        userPreferencesRepository = null;
    }

    public void sendPreferencesToDB(ArrayList<Integer> chosenPreferences){
        userPreferencesRepository.commitPreferencesToDB(chosenPreferences);
    }

    public void commitSelectedPreferencesToRealm(List selectedPreferences){
        userPreferencesRepository.commitSelectedPreferencesToRealm(selectedPreferences);
    }

   @Override
    public void onFinishedPreferencesDownload(ArrayList<UserPreferencesWithImage> allPreferences) {
        if (userPreferencesView != null){
            userPreferencesView.showData(allPreferences);
        }
    }

    @Override
    public void onDownloadFailed(String message) {
        userPreferencesView.onPreferencesDownloadFailed(message);
    }

    @Override
    public void onResponseFailure(String message) {
        userPreferencesView.onResponseFailure(message);
    }
}
