package com.example.arek.visium.screens.image_selection.image_carousel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.screens.image_selection.ImageSelectionPresenter;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by arek on 7/27/2017.
 */

public class ImageCarouselPresenterImpl implements ImageCarouselPresenter{

    private final ImageCarouselView view;
    private final ImageCarouselRepository repository;

    @Inject
    public ImageCarouselPresenterImpl(ImageCarouselView view,ImageCarouselRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onDetach(){
    }

    @Override
    public int getImageListSize(){
        return repository.getImagesFromExternalStorage().size();
    }

    @Override
    public void showImages() {
        view.showImages(repository.getImagesFromExternalStorage());
    }

}
