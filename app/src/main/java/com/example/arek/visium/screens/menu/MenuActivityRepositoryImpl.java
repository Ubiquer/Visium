package com.example.arek.visium.screens.menu;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.UserStorage;

import javax.inject.Inject;

/**
 * Created by arek on 1/26/2018.
 */

public class MenuActivityRepositoryImpl implements MenuActivityRepository {

    final RealmService realmService;

    @Inject
    public MenuActivityRepositoryImpl(RealmService realmService) {
        this.realmService = realmService;
    }

    @Override
    public boolean sessionTokenActive() {
        return realmService.tokenActive();
    }

    @Override
    public void deleteSessionToken() {
        realmService.deleteToken();
    }

}
