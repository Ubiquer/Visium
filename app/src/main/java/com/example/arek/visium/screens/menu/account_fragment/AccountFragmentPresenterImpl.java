package com.example.arek.visium.screens.menu.account_fragment;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.dao.Category;

import java.util.ArrayList;

/**
 * Created by arek on 2/25/2018.
 */

public class AccountFragmentPresenterImpl implements AccountFragmentPresenter{

    private final RealmService realmService;

    public AccountFragmentPresenterImpl(RealmService realmService) {

        this.realmService = realmService;
    }

    @Override
    public ArrayList<String> getPreferences() {
        return realmService.getPreferences();
    }

}
