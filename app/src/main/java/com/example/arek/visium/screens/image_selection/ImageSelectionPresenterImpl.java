package com.example.arek.visium.screens.image_selection;

import com.example.arek.visium.model.Category;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by arek on 11/12/2017.
 */

public class ImageSelectionPresenterImpl implements ImageSelectionPresenter, ImageSelectionRepository.OnUploadFinishedListener {

    private final ImageSelectionView view;
    private final ImageSelectionRepository repository;
    private String mToken;

    @Inject
    public ImageSelectionPresenterImpl(ImageSelectionRepository repository, ImageSelectionView view) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void uploadImage(String fileUri, String spinnerCategory){

        if (fileUri == null){

            view.insufficientData("Select image");

        } else if (spinnerCategory == null){

            view.insufficientData("Select category");

        } else if (fileUri ==null && spinnerCategory == null){

            view.insufficientData("Select image and category");

        } else{
            repository.uploadFile(fileUri, spinnerCategory, this);
        }
    }

    @Override
    public ArrayList<Category> getCategoriesFromRealm() {
        return repository.getCategoriesFromRealm();
    }

    @Override
    public void onUploadFinished(boolean uploadSuccessful, String message) {

        if (uploadSuccessful){
            view.onSuccessfulUpload(message);
        }else{
            view.onUploadError(message);
        }

    }

}