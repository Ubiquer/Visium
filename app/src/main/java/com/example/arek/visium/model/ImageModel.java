package com.example.arek.visium.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arek on 7/12/2017.
 */

public class ImageModel {

    @SerializedName("ImageBytes")
    String imageBytes;

    @SerializedName("Categoory")
    String category;

    public String getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
