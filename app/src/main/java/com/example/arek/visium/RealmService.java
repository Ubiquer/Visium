package com.example.arek.visium;

import android.util.Log;

import com.example.arek.visium.dao.Category;
import com.example.arek.visium.dao.UserData;
import com.example.arek.visium.dao.UserPreferencesWithImage;
import com.example.arek.visium.realm.CategoriesRealm;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.realm.UserPreferencesCategories;
import com.example.arek.visium.realm.UserRegistrationData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by arek on 1/17/2018.
 */

public class RealmService {

    private Realm realm;

    public RealmService(Realm realm) {
        this.realm = realm;
    }

    public void closeRealm(){
        realm.close();
    }

    /**CATEGORIES METHODS**/

    public void commitAllCategoriesToRealm(List allCategories){

        checkIfRealmIsOpened();

        realm.executeTransaction(realm -> {

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
        });

    }

    public void commitSelectedCategoriesToRealm(List selectedCategories) {

        checkIfRealmIsOpened();

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

        checkIfRealmIsOpened();

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

    /**TOKEN METHODS**/

    public void createOrUpdateToken(String token){

        checkIfRealmIsOpened();

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

    public String getAccessToken(){
    ArrayList<String> token = new ArrayList<>();
        realm.executeTransaction(realm1 -> {
            Token mToken = realm.where(Token.class).findFirst();
            token.add(mToken.getM_token());
        });
        return token.get(0);
    }

    public void deleteToken(){

        checkIfRealmIsOpened();

        realm.executeTransaction(realm1 -> {
            Token mToken = realm.where(Token.class).findFirst();
            if (mToken != null){
                mToken.deleteFromRealm();
            }

        });
    }

    public boolean tokenActive(){

        boolean isActive[] = {true};

        checkIfRealmIsOpened();

        realm.executeTransaction(realm1 -> {
            Token mToken = realm.where(Token.class).findFirst();
            if (mToken == null){
                isActive[0] = false;
            }
        });

        Log.d("active: ", String.valueOf(isActive[0]));
        return isActive[0];
    }

    /**PREFERENCES METHODS**/

    public boolean checkSavedPreferences() {

        boolean[] preferencesExist = new boolean[1];

        checkIfRealmIsOpened();

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

    public ArrayList<String> getPreferences(){

        ArrayList<String> categories = new ArrayList<>();

        checkIfRealmIsOpened();
        realm.executeTransaction(realm1 -> {
            UserPreferencesCategories listOfCategories = realm.where(UserPreferencesCategories.class).findFirst();
            RealmList<String> myPreferencedCategories = listOfCategories.getCategoriesList();
            if (myPreferencedCategories.size() > 0){
                for (int i=0; i<myPreferencedCategories.size(); i++){
                    categories.add(myPreferencedCategories.get(i));
                }
            }
        });
        return categories;
    }

    private void checkIfRealmIsOpened(){
        if (realm != Realm.getDefaultInstance()){
            realm = Realm.getDefaultInstance();
        }
    }

    /**User data**/
    //TODO: finish implementing user data storage (redo).

    public void setUserData(UserData userData){

        checkIfRealmIsOpened();

        realm.executeTransaction(realm1 -> {
            UserRegistrationData data = realm.where(UserRegistrationData.class).findFirst();
            if (data == null && userData != null){
                data.setName(userData.getName());
                data.setEmail(userData.getEmail());
                data.setPassword(userData.getPassword());
                if (userData.getSecondName()!= null){
                    data.setSecondName(userData.getSecondName());
                }
            }
        });

    }

    public UserData getUserData(){

        UserData userData = new UserData();

        realm.executeTransaction(realm1 -> {
            UserRegistrationData data = realm.where(UserRegistrationData.class).findFirst();
            if (data!=null){
                userData.setEmail(data.getEmail().toString());
                userData.setName(data.getName().toString());
                userData.setPassword(data.getPassword().toString());
                if (data.getSecondName()!= null){
                    userData.setSecondName(data.getSecondName().toString());
                }
            }
        });

        return userData;
    }

}
