package com.example.arek.visium.screens.image_selection;

import com.example.arek.visium.dao.Category;

import java.util.ArrayList;

/**
 * Created by arek on 12/13/2017.
 */

public interface ImageSelectionPresenter {

    void uploadImage(String fileUri, String spinnerCategory);
    ArrayList<Category> getCategoriesFromRealm();

}
