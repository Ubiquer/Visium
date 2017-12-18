package com.example.arek.visium.dependency_injection.screens.image_selection_di;

import com.example.arek.visium.screens.image_selection.ImageSelectionActivity;
import com.example.arek.visium.screens.image_selection.ImageSelectionPresenter;
import com.example.arek.visium.screens.image_selection.ImageSelectionPresenterImpl;
import com.example.arek.visium.screens.image_selection.ImageSelectionRepository;
import com.example.arek.visium.screens.image_selection.ImageSelectionRepositoryImpl;
import com.example.arek.visium.screens.image_selection.ImageSelectionView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arek on 12/13/2017.
 */
@Module
public class ImageSelectionModule {

    private final ImageSelectionActivity imageSelectionActivity;

    public ImageSelectionModule(ImageSelectionActivity imageSelectionActivity) {
        this.imageSelectionActivity = imageSelectionActivity;
    }

    @Provides
    @ImageSelectionScope
    ImageSelectionActivity activity(){
        return new ImageSelectionActivity();
    }

    @Provides
    @ImageSelectionScope
    ImageSelectionView view(){
        return imageSelectionActivity;
    }

    @Provides
    @ImageSelectionScope
    ImageSelectionRepository imageSelectionRepository(){
        return new ImageSelectionRepositoryImpl();
    }

    @Provides
    @ImageSelectionScope
    ImageSelectionPresenter presenter(ImageSelectionView view){
        return new ImageSelectionPresenterImpl(view);
    }


}
