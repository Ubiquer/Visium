package com.example.arek.visium.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Arek on 2017-06-28.
 */

public class UserLogin {

    @SerializedName("Email")
    @Expose
    private String mEmail;

    @SerializedName("Password")
    @Expose
    private String mPassword;

    public UserLogin(String mEmail, String mPassword) {

        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
