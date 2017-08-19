package com.example.arek.visium.realm;

import io.realm.RealmObject;

/**
 * Created by arek on 7/25/2017.
 */

public class UserRegistrationData extends RealmObject {

    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;

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
