package com.example.arek.visium.screens.menu;

import com.example.arek.visium.UserStorage;

/**
 * Created by arek on 1/26/2018.
 */

public class MenuActivityRepositoryImpl implements MenuActivityRepository {

    public MenuActivityRepositoryImpl() {
    }

    @Override
    public boolean checkSessionToken() {
        return false;
    }
}
