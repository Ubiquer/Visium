package com.example.arek.visium.dependency_injection.application;

import com.example.arek.visium.screens.CredentialsValidator;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arek on 1/7/2018.
 */
@Module
public class CredentialsValidationModule {

    @Provides
    @VisiumApplicationScope
    public CredentialsValidator credentialsValidator(){
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        return credentialsValidator;
    }

}
