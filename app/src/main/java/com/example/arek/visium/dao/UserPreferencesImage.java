package com.example.arek.visium.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arek on 7/8/2017.
 */

public class UserPreferencesImage implements Parcelable {


    private String imageName;
    private int imageId;
    private boolean isSelected;

//    public UserPreferencesImage(String imageName, int imageId, boolean isSelected) {
//        this.imageName = imageName;
//        this.imageId = imageId;
//        this.isSelected = isSelected;
//    }

    public UserPreferencesImage(Parcel in) {
        imageName = in.readString();
        imageId = in.readInt();
    }

    public static final Creator<UserPreferencesImage> CREATOR = new Creator<UserPreferencesImage>() {
        @Override
        public UserPreferencesImage createFromParcel(Parcel in) {
            return new UserPreferencesImage(in);
        }

        @Override
        public UserPreferencesImage[] newArray(int size) {
            return new UserPreferencesImage[size];
        }
    };

    public UserPreferencesImage() {

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
