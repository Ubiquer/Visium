package com.example.arek.visium.screens.menu;

/**
 * Created by arek on 1/26/2018.
 */

public interface MenuActivityRepository {

    boolean sessionTokenActive();
    void deleteSessionToken();

}
