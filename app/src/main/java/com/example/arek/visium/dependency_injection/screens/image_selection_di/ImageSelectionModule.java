package com.example.arek.visium.dependency_injection.screens.image_selection_di;

import android.content.ContentResolver;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.arek.visium.RealmService;
import com.example.arek.visium.dependency_injection.application.ApplicationContext;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.image_selection.ImageSelectionActivity;
import com.example.arek.visium.screens.image_selection.ImageSelectionPresenter;
import com.example.arek.visium.screens.image_selection.ImageSelectionPresenterImpl;
import com.example.arek.visium.screens.image_selection.ImageSelectionRepository;
import com.example.arek.visium.screens.image_selection.ImageSelectionRepositoryImpl;
import com.example.arek.visium.screens.image_selection.ImageSelectionView;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselAdapter;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselPresenter;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselPresenterImpl;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselRepository;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselView;

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
    ImageCarouselView imageCarouselView(){ return imageSelectionActivity; }

    @Provides
    @ImageSelectionScope
    ImageSelectionRepository imageSelectionRepository(VisiumService visiumService, RealmService realmService){
        return new ImageSelectionRepositoryImpl(visiumService, realmService);
    }

    @Provides
    @ImageSelectionScope
    ImageSelectionPresenter presenter(ImageSelectionRepository repository, ImageSelectionView view){
        return new ImageSelectionPresenterImpl(repository, view);
    }

    @Provides
    @ImageSelectionScope
    ImageCarouselRepository imageCarouselRepository(ContentResolver contentResolver){
        return new ImageCarouselRepository(contentResolver);
    }

    @Provides
    @ImageSelectionScope
    ImageCarouselPresenter imageCarouselPresenter(ImageCarouselView view, ImageCarouselRepository repository){
        return new ImageCarouselPresenterImpl(view, repository);
    }

    @Provides
    @ImageSelectionScope
    ImageCarouselAdapter adapter(){
        return new ImageCarouselAdapter();
    }


}
