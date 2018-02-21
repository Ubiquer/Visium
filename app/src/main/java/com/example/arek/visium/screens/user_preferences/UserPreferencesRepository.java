package com.example.arek.visium.screens.user_preferences;

import com.example.arek.visium.dao.UserPreferencesWithImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 11/15/2017.
 */

public interface UserPreferencesRepository {

    void commitPreferencesToDB(ArrayList<Integer> chosenPreferences);

    void commitSelectedCategoriesToRealm(List selectedPreferences);

    void commitAllCategoriesToRealm(List allCategories);

    interface OnDownLoadFinishedListener {
        void onLoadPreferences(ArrayList<UserPreferencesWithImage> allPreferences);
        void onLoadFailed(String message);
        void onLoadResponseFailure(String message);
    }

    void onDestroy();

    void loadPreferenceModels(OnDownLoadFinishedListener listener);

}
