package com.example.arek.visium.screens.user_preferences;

import com.example.arek.visium.model.UserPreferencesWithImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 11/15/2017.
 */

public interface UserPreferencesRepository {

    void commitPreferencesToDB(ArrayList<Integer> chosenPreferences);

    void commitSelectedPreferencesToRealm(List selectedPreferences);

    void commitAllCategoriesToRealm(List allCategories);

    interface OnDownLoadFinishedListener {

        void onFinishedPreferencesDownload(ArrayList<UserPreferencesWithImage> allPreferences);
        void onDownloadFailed(String message);
        void onResponseFailure(String message);

    }

    void loadPreferenceModels(OnDownLoadFinishedListener listener);

}
