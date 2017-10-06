package com.example.arek.visium.screens.image_duel;

/**
 * Created by arek on 10/5/2017.
 */

public interface ImageDuelView {

    void onImagesAccessed();
    void showProgressDialog();

    void setRecycylerAdapter(ImageDuelViewAdapter adapter);
}
