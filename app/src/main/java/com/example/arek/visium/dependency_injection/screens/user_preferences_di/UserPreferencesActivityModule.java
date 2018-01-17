package com.example.arek.visium.dependency_injection.screens.user_preferences_di;

import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.user_preferences.UserPreferencesActivity;
import com.example.arek.visium.screens.user_preferences.UserPreferencesPresenter;
import com.example.arek.visium.screens.user_preferences.UserPreferencesPresenterImpl;
import com.example.arek.visium.screens.user_preferences.UserPreferencesRepository;
import com.example.arek.visium.screens.user_preferences.UserPreferencesRepositoryImpl;
import com.example.arek.visium.screens.user_preferences.UserPreferencesView;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by arek on 1/15/2018.
 */
@Module
public class UserPreferencesActivityModule {

    private final UserPreferencesActivity activity;

    public UserPreferencesActivityModule(UserPreferencesActivity activity) {
        this.activity = activity;
    }

    @Provides
    @UserPreferencesActivityScope
    UserPreferencesView view(){
        return activity;
    }

    @Provides
    @UserPreferencesActivityScope
    UserPreferencesRepository repository(VisiumService visiumService){
        return new UserPreferencesRepositoryImpl(visiumService);
    }

    @Provides
    @UserPreferencesActivityScope
    UserPreferencesPresenter presenter(UserPreferencesView view, UserPreferencesRepository repository){
        return new UserPreferencesPresenterImpl(view, repository);
    }

}
