package com.example.arek.visium.dependency_injection.screens.login_di;


import com.example.arek.visium.dependency_injection.application.VisiumApplicationComponent;
import com.example.arek.visium.screens.login.LoginActivity;
import com.example.arek.visium.screens.login.LoginRepositoryImpl;

import dagger.Component;

/**
 * Created by arek on 12/5/2017.
 */
@LoginActivityScope
@Component(modules = LoginActivityModule.class, dependencies = VisiumApplicationComponent.class)
public interface LoginActivityComponent {

    void injectLoginActivity(LoginActivity loginActivity);

}
