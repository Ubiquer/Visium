package com.example.arek.visium.model;

/**
 * Created by arek on 7/8/2017.
 */

public class UserPreferencesImageModel {

    private String imageName;
    private int imageId;
    private boolean isSelected;

//    public UserPreferencesImageModel(String imageName, int imageId, boolean isSelected) {
//        this.imageName = imageName;
//        this.imageId = imageId;
//        this.isSelected = isSelected;
//    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
