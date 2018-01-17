package com.example.arek.visium.screens.user_preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 1/16/2018.
 */

public interface UserPreferencesPresenter {

    void onCreate();
    void onDestroy();
    void sendPreferencesToDB(ArrayList<Integer> chosenPreferences);
    void commitSelectedPreferencesToRealm(List selectedPreferences);

}
