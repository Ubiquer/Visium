package com.example.arek.visium.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by arek on 10/31/2017.
 */

public class Photographer {

    @SerializedName("Name")
    private String name;
    @SerializedName("Surname")
    private String surname;
    @SerializedName("AvatarPath")
    private String avatarPath;
    @SerializedName("Points")
    private int points;
    @SerializedName("Email")
    private String email;
    @SerializedName("Position")
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
