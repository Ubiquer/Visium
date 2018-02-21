package com.example.arek.visium.screens.user_preferences;

import com.example.arek.visium.dao.UserPreferencesWithImage;

import java.util.ArrayList;

/**
 * Created by arek on 11/4/2017.
 */

public interface UserPreferencesView {

    void showData(ArrayList<UserPreferencesWithImage> userPreferencesWithImages);
    void onPreferencesDownloadFailed(String message);
    void onResponseFailure(String message);
    void sendPreferencesToDB();

}
