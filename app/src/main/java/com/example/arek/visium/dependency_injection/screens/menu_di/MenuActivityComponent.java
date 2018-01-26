package com.example.arek.visium.dependency_injection.screens.menu_di;

import com.example.arek.visium.dependency_injection.application.VisiumApplicationComponent;
import com.example.arek.visium.screens.menu.MenuActivity;

import dagger.Component;

/**
 * Created by arek on 12/18/2017.
 */
@MenuActivityScope
@Component(modules = MenuActivityModule.class, dependencies = VisiumApplicationComponent.class)
public interface MenuActivityComponent {

    void injectMenuActivity(MenuActivity menuActivity);

}
