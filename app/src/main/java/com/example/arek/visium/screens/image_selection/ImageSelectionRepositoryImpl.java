package com.example.arek.visium.screens.image_selection;

import android.os.Environment;
import android.util.Log;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.dao.Category;
import com.example.arek.visium.rest.VisiumService;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 11/13/2017.
 */

public class ImageSelectionRepositoryImpl implements ImageSelectionRepository{

    private Call<ResponseBody> uploadImageCall;
    private final VisiumService visiumService;
    private final RealmService realmService;
    private String token;
    private String fileUri;

    @Inject
    public ImageSelectionRepositoryImpl(VisiumService visiumService, RealmService realmService) {
        this.visiumService = visiumService;
        this.realmService = realmService;
    }

    @Override
    public void uploadFile(String fileUri, String spinnerCategory, OnUploadFinishedListener onUploadFinishedListener) {

        this.fileUri = fileUri;

        uploadImageCall = null;
        if (uploadImageCall == null){

            File file = new File(fileUri);
            File originalFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileUri);
            token = realmService.getAccessToken();
            String originalFileName = originalFile.getName();
            Log.d("fName: ", originalFileName);
            RequestBody filePart = RequestBody.create(
                    MediaType.parse("image/*"),
                    file);

            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);
            uploadImageCall = visiumService.uploadImage(token, spinnerCategory, fileToUpload);
                uploadImageCall.enqueue(new Callback<ResponseBody>() {
                    boolean uploadSuccessful = false;

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            uploadSuccessful = true;
                            onUploadFinishedListener.onUploadFinished(uploadSuccessful, response.message());
                        }
                        else {
                            onUploadFinishedListener.onUploadFinished(uploadSuccessful, response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onUploadFinishedListener.onUploadFinished(uploadSuccessful, t.getMessage());
                    }
                });
        }
    }

    @Override
    public ArrayList<Category> getCategoriesFromRealm() {

        return realmService.getCategories();

    }
}
