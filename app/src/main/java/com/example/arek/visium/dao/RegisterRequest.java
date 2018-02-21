package com.example.arek.visium.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Arek on 2017-06-25.
 */

public class RegisterRequest {


    @SerializedName("Email")
    @Expose
    private String mEmail;
    @SerializedName("Password")
    @Expose
    private String mPassword;
    @SerializedName("ConfirmPassword")
    @Expose
    private String mConfirmPassword;

    public RegisterRequest(){}

    public RegisterRequest(String email, String password, String confirmPassword) {

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
