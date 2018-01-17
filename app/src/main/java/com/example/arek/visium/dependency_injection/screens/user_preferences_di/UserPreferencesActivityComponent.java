package com.example.arek.visium.dependency_injection.screens.user_preferences_di;

import com.example.arek.visium.dependency_injection.application.VisiumApplicationComponent;
import com.example.arek.visium.screens.user_preferences.UserPreferencesActivity;

import dagger.Component;

/**
 * Created by arek on 1/15/2018.
 */
@UserPreferencesActivityScope
@Component(modules = UserPreferencesActivityModule.class, dependencies = VisiumApplicationComponent.class)
public interface UserPreferencesActivityComponent {

    void injectUserPreferencesActivity(UserPreferencesActivity activity);

}
