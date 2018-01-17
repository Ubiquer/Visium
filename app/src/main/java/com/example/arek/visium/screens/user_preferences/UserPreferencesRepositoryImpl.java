package com.example.arek.visium.screens.user_preferences;

import android.content.Context;
import android.util.Log;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.model.UserPreferencesWithImage;
import com.example.arek.visium.realm.CategoriesRealm;
import com.example.arek.visium.realm.UserPreferencesCategories;
import com.example.arek.visium.rest.VisiumService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 11/15/2017.
 */

public class UserPreferencesRepositoryImpl implements UserPreferencesRepository {

    private final VisiumService visiumService;
    private final RealmService realmService;
    private Call<String> sendPreferencesCall;
    private ArrayList<UserPreferencesWithImage> userPreferencesWithImages;

    @Inject
    public UserPreferencesRepositoryImpl(VisiumService visiumService, RealmService realmService) {
        this.visiumService = visiumService;
        this.realmService = realmService;
    }

    @Override
    public void commitPreferencesToDB(ArrayList<Integer> chosenPreferences) {

        if (sendPreferencesCall == null){
            sendPreferencesCall = visiumService.sendPreferences(chosenPreferences);
            sendPreferencesCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        Log.d("Successfull call: ", response.message());
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void commitSelectedCategoriesToRealm(List selectedCategories) {

        realmService.commitSelectedCategoriesToRealm(selectedCategories);
    }

    @Override
    public void commitAllCategoriesToRealm(List allCategories) {

        realmService.commitAllCategoriesToRealm(allCategories);
    }

    @Override
    public void onDestroy() {
        realmService.closeRealm();
    }

    @Override
    public void loadPreferenceModels(OnDownLoadFinishedListener listener) {

        visiumService.getUserPreferences().enqueue(new Callback<List<UserPreferencesWithImage>>() {
            @Override
            public void onResponse(Call<List<UserPreferencesWithImage>> call, Response<List<UserPreferencesWithImage>> response) {

                if(response.isSuccessful()) {
                    userPreferencesWithImages = (ArrayList<UserPreferencesWithImage>) response.body();
                    commitAllCategoriesToRealm(userPreferencesWithImages);
                    listener.onLoadPreferences(userPreferencesWithImages);
                }else {
                    listener.onLoadFailed(response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<List<UserPreferencesWithImage>> call, Throwable t) {

                listener.onLoadResponseFailure(t.getMessage());

            }
        });
    }

}
