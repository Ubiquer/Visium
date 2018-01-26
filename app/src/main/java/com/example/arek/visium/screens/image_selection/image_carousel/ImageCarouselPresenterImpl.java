package com.example.arek.visium.screens.image_selection.image_carousel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.arek.visium.VisiumApplication;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by arek on 7/27/2017.
 */

public class ImageCarouselPresenterImpl {

    private ImageCarouselView imageCarouselView;
    private ImageCarouselRepository imageCarouselRepository;

    public ImageCarouselPresenterImpl() {
    }

    public void onAttach(ImageCarouselView imageCarouselView){
        this.imageCarouselView = imageCarouselView;
        imageCarouselRepository = new ImageCarouselRepository();
        imageCarouselView.showImages(imageCarouselRepository.getImagesFromExternalStorage());
    }

    public void onDetach(){
        imageCarouselView = null;
        imageCarouselRepository = null;
    }

    public int getImageListSize(){
        return imageCarouselRepository.getImagesFromExternalStorage().size();
    }

}
