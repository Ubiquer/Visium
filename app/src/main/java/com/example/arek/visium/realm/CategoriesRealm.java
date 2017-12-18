package com.example.arek.visium.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arek on 11/6/2017.
 */

public class CategoriesRealm extends RealmObject {

    @PrimaryKey
    private String id;

    RealmList<String> allCategories;

    public RealmList<String> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(RealmList<String> allCategories) {
        this.allCategories = allCategories;
    }
}
