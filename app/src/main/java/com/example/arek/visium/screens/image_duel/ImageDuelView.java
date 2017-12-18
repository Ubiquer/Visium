package com.example.arek.visium.screens.image_duel;

import com.example.arek.visium.model.DuelImage;

import java.util.ArrayList;

/**
 * Created by arek on 10/5/2017.
 */

public interface ImageDuelView {

    void onImagesAccessed();
    void showProgressDialog();
    void showData(ArrayList<DuelImage> duelImagesList);
}
