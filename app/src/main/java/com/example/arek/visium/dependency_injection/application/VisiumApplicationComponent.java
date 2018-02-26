package com.example.arek.visium.dependency_injection.application;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.arek.visium.RealmService;
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
@Component(modules = {VisiumServiceModule.class, PicassoModule.class, SharedPreferencesModule.class, UserStorageModule.class,
        CredentialsValidationModule.class, CompositeDisposableModule.class, ContentResolverModule.class})
public interface VisiumApplicationComponent {

    SharedPreferences getSharedPreferences();
    Picasso getPicasso();
    VisiumService getVisiumService();
    RealmService getRealmService();
    UserStorage getUserStorage();
    Realm getRealm();
    ContentResolver getContentResolver();
    CredentialsValidator getCredentialsValidator();
}
