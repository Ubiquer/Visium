package com.example.arek.visium.screens.image_selection;

import com.example.arek.visium.model.Category;

import java.util.ArrayList;

/**
 * Created by arek on 11/12/2017.
 */

public class ImageSelectionPresenterImpl implements ImageSelectionPresenter, ImageSelectionRepository.OnUploadFinishedListener {

    private ImageSelectionView imageSelectionView;
    private ImageSelectionRepositoryImpl imageSelectionRepositoryImpl;
    private String mToken;

    public ImageSelectionPresenterImpl(ImageSelectionView imageSelectionView) {
        this.imageSelectionView = imageSelectionView;
    }

    @Override
    public void onAttach(ImageSelectionView imageSelectionView) {
        this.imageSelectionView = imageSelectionView;
        imageSelectionRepositoryImpl = new ImageSelectionRepositoryImpl();
    }

    @Override
    public void onDetach() {
        imageSelectionView = null;
        imageSelectionRepositoryImpl = null;
    }

    @Override
    public void uploadImage(String fileUri, String spinnerCategory){

        if (fileUri == null){

            imageSelectionView.insufficientData("Select image");

        } else if (spinnerCategory == null){

            imageSelectionView.insufficientData("Select category");

        } else if (fileUri ==null && spinnerCategory == null){

            imageSelectionView.insufficientData("Select image and category");

        } else{
            imageSelectionRepositoryImpl.uploadFile(fileUri, spinnerCategory, this);
        }
    }

    @Override
    public ArrayList<Category> getCategoriesFromRealm() {
        return imageSelectionRepositoryImpl.getCategoriesFromRealm();
    }

    @Override
    public void onUploadFinished(boolean uploadSuccessful, String message) {

        if (uploadSuccessful){
            imageSelectionView.onSuccessfulUpload(message);
        }else{
            imageSelectionView.onUploadError(message);
        }

    }

}