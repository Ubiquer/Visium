package com.example.arek.visium.dependency_injection.screens.menu_di;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.screens.login.LoginActivityView;
import com.example.arek.visium.screens.menu.MenuActivity;
import com.example.arek.visium.screens.menu.MenuActivityPresenter;
import com.example.arek.visium.screens.menu.MenuActivityPresenterImpl;
import com.example.arek.visium.screens.menu.MenuActivityRepository;
import com.example.arek.visium.screens.menu.MenuActivityRepositoryImpl;
import com.example.arek.visium.screens.menu.MenuActivityView;

import dagger.Module;
import dagger.Provides;
import io.realm.annotations.PrimaryKey;

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

    @Provides
    @MenuActivityScope
    MenuActivityView view(){
        return menuActivity;
    }

    @Provides
    @MenuActivityScope
    MenuActivityPresenter presenter(MenuActivityView view, MenuActivityRepository repository){
        return new MenuActivityPresenterImpl(view, repository);
    }

    @Provides
    @MenuActivityScope
    MenuActivityRepository repository(RealmService realmService){
        return new MenuActivityRepositoryImpl(realmService);
    }


}
