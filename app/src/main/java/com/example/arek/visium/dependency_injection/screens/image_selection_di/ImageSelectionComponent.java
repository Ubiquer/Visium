package com.example.arek.visium.dependency_injection.screens.image_selection_di;

import com.example.arek.visium.dependency_injection.application.VisiumApplicationComponent;
import com.example.arek.visium.screens.image_selection.ImageSelectionActivity;

import javax.inject.Scope;

import dagger.Component;

/**
 * Created by arek on 12/12/2017.
 */
@ImageSelectionScope
@Component(modules = ImageSelectionModule.class, dependencies = VisiumApplicationComponent.class)
public interface ImageSelectionComponent {

    void injectImageSelectionActivity(ImageSelectionActivity activity);

}
