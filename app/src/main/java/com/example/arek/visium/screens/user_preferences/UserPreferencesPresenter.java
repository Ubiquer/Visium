package com.example.arek.visium.screens.user_preferences;

import android.content.Context;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.model.UserPreferencesWithImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 11/4/2017.
 */

public class UserPreferencesPresenter implements UserPreferencesRepository.OnDownLoadFinishedListener{

    private UserPreferencesView userPreferencesView;
    private UserPreferencesRepository userPreferencesRepository;
    private Context context;

    public UserPreferencesPresenter() {
        context = VisiumApplication.getContext();
    }

    public void onAttach(UserPreferencesView userPreferencesView){
        this.userPreferencesView = userPreferencesView;
//        userPreferencesRepository = ((VisiumApplication) context).getUserPreferencesRepository();
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
    public void onLoadPreferences(ArrayList<UserPreferencesWithImage> allPreferences) {
        if (userPreferencesView != null){
            userPreferencesView.showData(allPreferences);
        }
    }

    @Override
    public void onLoadFailed(String message) {
        userPreferencesView.onPreferencesDownloadFailed(message);
    }

    @Override
    public void onLoadResponseFailure(String message) {
        userPreferencesView.onResponseFailure(message);
    }
}
