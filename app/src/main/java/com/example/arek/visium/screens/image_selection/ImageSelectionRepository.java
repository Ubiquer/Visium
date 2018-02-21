package com.example.arek.visium.screens.image_selection;

import com.example.arek.visium.dao.Category;

import java.util.ArrayList;

/**
 * Created by arek on 11/23/2017.
 */

public interface ImageSelectionRepository {

    void uploadFile(String fileUri, String spinnerCategory, OnUploadFinishedListener onUploadFinishedListener);
    ArrayList<Category> getCategoriesFromRealm();

    interface OnUploadFinishedListener{
        void onUploadFinished(boolean uploadSuccessful, String message);
    }

}
