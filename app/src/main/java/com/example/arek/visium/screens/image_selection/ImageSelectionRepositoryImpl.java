package com.example.arek.visium.screens.image_selection;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.model.Category;
import com.example.arek.visium.realm.CategoriesRealm;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.rest.VisiumService;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
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

    private Realm realm;
    private String mToken;
    private Context context;
    private Call<ResponseBody> uploadImageCall;
    private VisiumService visiumService;
    private RealmService realmService;
    private String token;
    private String fileUri;
    private static final int MY_PERMISSIONS_REQUEST = 100;

    public ImageSelectionRepositoryImpl() {
        context = VisiumApplication.getContext();
        visiumService = ((VisiumApplication) context).getVisiumService();
        realmService = ((VisiumApplication) context).getRealmService();
    }


//    File originalFile = FileUtils.getFile(this, fileUri);
//    RequestBody filePart = RequestBody.create(
//            MediaType.parse(context.getContentResolver().getType(fileUri)),
//            originalFile);
    @Override
    public void uploadFile(String fileUri, String spinnerCategory, OnUploadFinishedListener onUploadFinishedListener) {

        this.fileUri = fileUri;

        uploadImageCall = null;
        if (uploadImageCall == null){

            File file = new File(fileUri);
            File originalFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileUri);
            token = getAccessToken();
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
//        ArrayList<Category> categoriesList = new ArrayList<>();
//        realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        CategoriesRealm categoriesRealm = realm.where(CategoriesRealm.class).findFirst();
//        RealmList<String> categories = categoriesRealm.getAllCategories();
//
//        for (int i=0; i<categories.size(); i++){
//
//            Category category = new Category();
//            category.setCategoryName(categories.get(i));
//            categoriesList.add(category);
//        }
//
//        realm.commitTransaction();
//        realm.close();
//        return categoriesList;
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
