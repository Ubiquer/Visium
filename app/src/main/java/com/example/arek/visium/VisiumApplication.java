package com.example.arek.visium;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by arek on 7/14/2017.
 */

public class VisiumApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}