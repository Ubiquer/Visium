package com.example.arek.visium.screens.menu;

import javax.inject.Inject;

/**
 * Created by arek on 1/26/2018.
 */

public class MenuActivityPresenterImpl implements MenuActivityPresenter {

    private final MenuActivityView view;
    private final MenuActivityRepository repository;

    @Inject
    public MenuActivityPresenterImpl(MenuActivityView view, MenuActivityRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void deleteSessionToken() {
        repository.deleteSessionToken();
    }

    @Override
    public boolean sessionTokenActive() {
        return repository.sessionTokenActive();
    }


}
