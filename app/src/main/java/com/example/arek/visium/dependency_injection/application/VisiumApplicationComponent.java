package com.example.arek.visium.dependency_injection.application;

import android.content.SharedPreferences;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.dependency_injection.network.VisiumServiceModule;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.CredentialsValidator;
import com.squareup.picasso.Picasso;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by arek on 12/2/2017.
 */
@VisiumApplicationScope
@Component(modules = {VisiumServiceModule.class, PicassoModule.class, SharedPreferencesModule.class, UserStorageModule.class, CredentialsValidationModule.class, CompositeDisposableModule.class})
public interface VisiumApplicationComponent {


    SharedPreferences getSharedPreferences();
    Picasso getPicasso();
    VisiumService getVisiumService();
    UserStorage getUserStorage();
    Realm getRealm();
    CredentialsValidator getCredentialsValidator();
    CompositeDisposable getCompositeDisposable();

}
