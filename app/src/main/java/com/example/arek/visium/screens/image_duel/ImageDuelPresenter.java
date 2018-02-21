package com.example.arek.visium.screens.image_duel;


import android.util.Log;

import com.example.arek.visium.dao.DuelImage;
import com.example.arek.visium.rest.VisiumService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arek on 10/5/2017.
 */

public class ImageDuelPresenter {

    private VisiumService mVisiumService;
    private Retrofit mRetrofit;
    private Call<List<DuelImage>> imagesCall;
    private Call<ResponseBody> imageDuelResultCall;
    private static final String CATEGORY = "Samochody";
    private static final String CATEGORY_MOUNTAINS = "Góry";
    private ImageDuelViewAdapter imageDuelViewAdapter;
    private ArrayList<DuelImage> duelImage;
    private ImageDuelView mImageDuelView;

    public ImageDuelPresenter(VisiumService mVisiumService, Retrofit retrofit) {
        this.mVisiumService = mVisiumService;
        this.mRetrofit = retrofit;
    }

    public void onAttach(ImageDuelView imageDuelView){
        this.mImageDuelView = imageDuelView;
        getDuelImages();
    }

    public void onDetach(){
        this.mImageDuelView = null;
    }

    private void getDuelImages(){
        if (imagesCall == null){
            imagesCall = mVisiumService.getDuelImages(CATEGORY_MOUNTAINS);
            imagesCall.enqueue(new Callback<List<DuelImage>>() {
                @Override
                public void onResponse(Call<List<DuelImage>> call, Response<List<DuelImage>> response) {
                    if (response.isSuccessful()){
                        duelImage = (ArrayList<DuelImage>) response.body();
                        mImageDuelView.showData(duelImage);
                    }else {
                        Log.d("failure: ", "");
                    }
                }
                @Override
                public void onFailure(Call<List<DuelImage>> call, Throwable t) {

                    Log.d("failed: ", " ");
                }
            });
        }

    }

    public void onImageChosen(int winnerId, int loserId) {

        Log.d("winner: ", String.valueOf(winnerId));
        Log.d("loser: ", String.valueOf(loserId));

        if (imageDuelResultCall==null){
            imageDuelResultCall = mVisiumService.postDuelResult(loserId, winnerId);
            imageDuelResultCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Log.d("Posted result response: ", response.message());
                        imagesCall = null;
                        imageDuelResultCall = null;
                        getDuelImages();
                    }else{
                        Log.d("Went wrong: ", response.message());
                        imageDuelResultCall = null;
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    imageDuelResultCall = null;
                }
            });
        }

    }
}
