package com.example.arek.visium.screens.image_selection;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import org.apache.commons.io.FileUtils;

import java.io.File;

import io.realm.Realm;
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

public class ImageSelectionRepository {

    private Realm realm;
    private String mToken;
    private Context context;
    private Call<ResponseBody> uploadImageCall;
    private ApiInterface apiInterface;
    private String token;
    private String fileUri;

    public ImageSelectionRepository() {
        context = VisiumApplication.getContext();
    }


//    File originalFile = FileUtils.getFile(this, fileUri);
//    RequestBody filePart = RequestBody.create(
//            MediaType.parse(context.getContentResolver().getType(fileUri)),
//            originalFile);

    public void uploadFile(String fileUri, String spinnerCategory) {

        this.fileUri = fileUri;

        uploadImageCall = null;
        if (uploadImageCall == null){

            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), fileUri);

//            Uri image = Uri.parse(String.valueOf(file));
            Uri imageUri = Uri.fromFile(file);
//            File originalFile = FileUtils.getFile(context.getFilesDir(), fileUri);
            File originalFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileUri);
            token = getAccessToken();
//        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, spinnerCategory);
            RequestBody filePart = RequestBody.create(
                    MediaType.parse(context.getContentResolver().getType(imageUri)),
                    file);

            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);
            uploadImageCall = apiInterface.uploadImage(token, spinnerCategory, fileToUpload);

            uploadImageCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    response.message();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public String getAccessToken(){

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Token token = realm.where(Token.class).findFirst();
        mToken = token.getM_token();
        realm.commitTransaction();
        realm.close();

        return mToken;
    }

}
