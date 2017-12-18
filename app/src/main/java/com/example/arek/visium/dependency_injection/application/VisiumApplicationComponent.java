package com.example.arek.visium.dependency_injection.application;

import com.example.arek.visium.dependency_injection.network.VisiumServiceModule;
import com.example.arek.visium.rest.VisiumService;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by arek on 12/2/2017.
 */
@Component(modules = VisiumServiceModule.class)
public interface ApplicationComponent {

    Picasso getPicasso();
    VisiumService getApiInterface();

}
