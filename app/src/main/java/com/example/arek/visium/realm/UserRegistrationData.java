package com.example.arek.visium.realm;

import io.realm.RealmObject;

/**
 * Created by arek on 7/25/2017.
 */

public class UserRegistrationData extends RealmObject {

    private String email;
    private String password;
    private String name;
    private String secondName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
