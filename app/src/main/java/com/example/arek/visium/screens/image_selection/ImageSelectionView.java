package com.example.arek.visium.screens.image_selection;

import android.net.Uri;

/**
 * Created by arek on 11/12/2017.
 */

public interface ImageSelectionView {

    void onSuccessfulUpload(String message);
    void onUploadError(String message);
    void insufficientData(String message);

}
