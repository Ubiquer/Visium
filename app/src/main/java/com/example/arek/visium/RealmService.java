package com.example.arek.visium;

import android.util.Log;

import com.example.arek.visium.model.Category;
import com.example.arek.visium.model.UserPreferencesWithImage;
import com.example.arek.visium.realm.CategoriesRealm;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.realm.UserPreferencesCategories;
import com.example.arek.visium.screens.login.LoginRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by arek on 1/17/2018.
 */

public class RealmService {

    private final Realm realm;

    public RealmService(Realm realm) {
        this.realm = realm;
    }

    public void closeRealm(){
        realm.close();
    }

    public void commitAllCategoriesToRealm(List allCategories){

        realm.executeTransaction(new Realm.Transaction(){

            @Override
            public void execute(final Realm realm) {

                RealmList<String> categories = new RealmList<>();
                for (int i = 0; i<allCategories.size(); i++){
                    UserPreferencesWithImage userPreferencesWithImage = (UserPreferencesWithImage) allCategories.get(i);
                    String category = userPreferencesWithImage.getCategoryName();
                    categories.add(category);
                }
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
            }
        });

    }

    public void commitSelectedCategoriesToRealm(List selectedCategories) {

        realm.executeTransaction(realm1 -> {

            RealmList<String> selectedPreferencesRealm = new RealmList<>();
            for (int i = 0; i<selectedCategories.size(); i++ ){
                String preferenceName = (String) selectedCategories.get(i);
                selectedPreferencesRealm.add(preferenceName);
            }
            try{
                UserPreferencesCategories categoriesList = realm1.createObject(UserPreferencesCategories.class, UUID.randomUUID().toString());
                categoriesList.setCategoriesList(selectedPreferencesRealm);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    public ArrayList<Category> getCategories(){

        ArrayList<Category> categoriesList = new ArrayList<>();

        realm.executeTransaction(realm1 -> {
            CategoriesRealm categoriesRealm = realm.where(CategoriesRealm.class).findFirst();
            RealmList<String> categories = categoriesRealm.getAllCategories();

            for (int i=0; i<categories.size(); i++){

                Category category = new Category();
                category.setCategoryName(categories.get(i));
                categoriesList.add(category);
            }
        });

        return categoriesList;

    }

    public void createOrUpdateToken(String token){

        realm.executeTransaction(realm1 -> {
            Token mToken = realm.where(Token.class).findFirst();
            if (mToken == null){
                mToken = realm.createObject(Token.class);
                mToken.setM_token(token);
            }else{
                mToken.setM_token(token);
                Log.d("my token: ", mToken.getM_token());
            }
        });
    }

    public void deleteToken(){
        realm.executeTransaction(realm1 -> {
            Token mToken = realm.where(Token.class).findFirst();
            if (mToken != null){
                mToken = null;
            }
        });
    }

    public boolean tokenActive(){

        final boolean[] isActive = {true};

        realm.executeTransaction(realm1 -> {
            Token mToken = realm.where(Token.class).findFirst();
            if (mToken == null){
                isActive[0] = false;
            }
        });

        return isActive[0];
    }

    public boolean checkSavedPreferences() {

        final boolean[] preferencesExist = new boolean[1];

        realm.executeTransaction(realm1 -> {
            List<UserPreferencesCategories> listOfCategories = realm.where(UserPreferencesCategories.class).findAll();
            if (listOfCategories.size() != 0){
                preferencesExist[0] = true;
            }else {
                preferencesExist[0] = false;
            }
        });

        return preferencesExist[0];
    }

}
