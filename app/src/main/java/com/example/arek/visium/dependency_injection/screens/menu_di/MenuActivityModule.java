package com.example.arek.visium.dependency_injection.screens.menu_di;

import com.example.arek.visium.screens.menu.MenuActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arek on 12/18/2017.
 */
@Module
public class MenuActivityModule {

    private final MenuActivity menuActivity;


    public MenuActivityModule(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
    }

    @Provides
    @MenuActivityScope
    MenuActivity menuActivity(){
        return menuActivity;
    }



}
