package com.example.arek.visium.realm;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arek on 7/24/2017.
 */

public class UserPreferencesCategories extends RealmObject{

    @PrimaryKey
    private String id;

    RealmList <String> categoriesList;

    public RealmList<String> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(RealmList<String> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
