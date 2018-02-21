package com.example.arek.visium.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arek on 7/18/2017.
 */

public class UserPreferencesWithImage{

    @SerializedName("CategoryId")
    @Expose
    private int categoryId;
    @SerializedName("Name")
    @Expose
    private String categoryName;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
