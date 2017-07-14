package com.example.arek.visium.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Arek on 2017-06-25.
 */

public class UserRegistration  {

    @SerializedName("Email")
    @Expose
    private String mEmail;
    @SerializedName("Password")
    @Expose
    private String mPassword;
    @SerializedName("ConfirmPassword")
    @Expose
    private String mConfirmPassword;

    public UserRegistration(){}

    public UserRegistration(String email, String password, String confirmPassword) {

        mEmail = email;
        mPassword = password;
        mConfirmPassword = confirmPassword;

    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmConfirmPassword() {
        return mConfirmPassword;
    }

    public void setmConfirmPassword(String mConfirmPassword) {
        this.mConfirmPassword = mConfirmPassword;
    }
}
