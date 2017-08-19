package com.example.arek.visium.realm;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arek on 7/24/2017.
 */

public class ListOfCategories {

//    @PrimaryKey
    int id;
    ArrayList<String> categoriesList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(ArrayList<String> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
