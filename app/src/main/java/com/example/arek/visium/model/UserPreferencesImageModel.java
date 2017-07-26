package com.example.arek.visium.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arek on 7/8/2017.
 */

public class UserPreferencesImageModel implements Parcelable {


    private String imageName;
    private int imageId;
    private boolean isSelected;

//    public UserPreferencesImageModel(String imageName, int imageId, boolean isSelected) {
//        this.imageName = imageName;
//        this.imageId = imageId;
//        this.isSelected = isSelected;
//    }

    public UserPreferencesImageModel(Parcel in) {
        imageName = in.readString();
        imageId = in.readInt();
    }

    public static final Creator<UserPreferencesImageModel> CREATOR = new Creator<UserPreferencesImageModel>() {
        @Override
        public UserPreferencesImageModel createFromParcel(Parcel in) {
            return new UserPreferencesImageModel(in);
        }

        @Override
        public UserPreferencesImageModel[] newArray(int size) {
            return new UserPreferencesImageModel[size];
        }
    };

    public UserPreferencesImageModel() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(imageId);

    }

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
