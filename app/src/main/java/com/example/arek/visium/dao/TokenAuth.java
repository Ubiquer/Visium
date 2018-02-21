package com.example.arek.visium.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arek on 7/11/2017.
 */

public class TokenAuth {

    @SerializedName("Token")
    @Expose
    private String token;

    public TokenAuth(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
