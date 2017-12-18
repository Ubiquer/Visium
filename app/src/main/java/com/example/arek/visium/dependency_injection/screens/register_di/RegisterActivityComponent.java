package com.example.arek.visium.dependency_injection.screens.register_di;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.dependency_injection.application.VisiumApplicationComponent;
import com.example.arek.visium.screens.register.RegisterActivity;

import dagger.Component;

/**
 * Created by arek on 12/12/2017.
 */
@RegisterActivityScope
@Component(modules = RegisterActivityModule.class, dependencies = VisiumApplicationComponent.class)
public interface RegisterActivityComponent {

    void injectRegisterActivity(RegisterActivity registerActivity);

}
