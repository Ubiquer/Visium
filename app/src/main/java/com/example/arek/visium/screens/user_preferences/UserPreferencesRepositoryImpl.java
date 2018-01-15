package com.example.arek.visium.screens.user_preferences;

import android.content.Context;
import android.util.Log;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.model.UserPreferencesWithImage;
import com.example.arek.visium.realm.CategoriesRealm;
import com.example.arek.visium.realm.UserPreferencesCategories;
import com.example.arek.visium.rest.VisiumService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 11/15/2017.
 */

public class UserPreferencesRepositoryImpl implements UserPreferencesRepository {

    private VisiumService visiumService;
    private Context context;
    private Realm realm;
    private Call<String> sendPreferencesCall;
    private ArrayList<UserPreferencesWithImage> userPreferencesWithImages;

    public UserPreferencesRepositoryImpl() {

        context = VisiumApplication.getContext();
        visiumService = ((VisiumApplication) context).getVisiumService();

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
    public void commitSelectedPreferencesToRealm(List selectedPreferences) {

        RealmList<String> selectedPreferencesRealm = new RealmList<>();
        for (int i = 0; i<selectedPreferences.size(); i++ ){
            String preferenceName = (String) selectedPreferences.get(i);
            selectedPreferencesRealm.add(preferenceName);
        }
        try{
            Log.d("realm prefs: ",selectedPreferencesRealm.toArray().toString());
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            UserPreferencesCategories categoriesList = realm.createObject(UserPreferencesCategories.class, UUID.randomUUID().toString());
            categoriesList.setCategoriesList(selectedPreferencesRealm);
            realm.commitTransaction();
            realm.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void commitAllCategoriesToRealm(List allPreferences) {

        RealmList<String> categories = new RealmList<>();
        for (int i = 0; i<allPreferences.size(); i++){
            UserPreferencesWithImage userPreferencesWithImage = (UserPreferencesWithImage) allPreferences.get(i);
            String category = userPreferencesWithImage.getCategoryName();
            categories.add(category);
        }
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CategoriesRealm categoriesRealm = realm.where(CategoriesRealm.class).findFirst();
        if (categoriesRealm == null)
        {
            categoriesRealm = realm.createObject(CategoriesRealm.class, UUID.randomUUID().toString());
            categoriesRealm.setAllCategories(categories);
        }
        else
        {
            categoriesRealm.setAllCategories(categories);
        }
        realm.commitTransaction();
        realm.close();
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
