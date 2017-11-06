package com.example.arek.visium.screens.image_duel;


import android.media.Image;

import com.example.arek.visium.R;
import com.example.arek.visium.RxBus;
import com.example.arek.visium.model.ImageDuelModel;
import com.example.arek.visium.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.functions.Action1;

/**
 * Created by arek on 10/5/2017.
 */

public class ImageDuelManager {

    private ApiInterface mApiInterface;
    private Retrofit mRetrofit;
    Call<List<ImageDuelModel>> imagesCall;
    private static final String CATEGORY = "Samochody";
    private ImageDuelViewAdapter imageDuelViewAdapter;
    private ArrayList<ImageDuelModel> duelImage;
    private final String rightColorCode = String.valueOf(R.string.rightColorCode);
    private final String leftColorCode = String.valueOf(R.string.leftColorCode);
    private SwipedItemParams swipedItemParams;
    private int viewHolderAdapterPosition;
    private int swipeDirection;
    private final int LEFT = 0;
    private final int RIGHT = 1;
    private ImageDuelView mImageDuelView;

    public ImageDuelManager(ApiInterface mApiInterface, Retrofit retrofit) {
        this.mApiInterface = mApiInterface;
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
//I have to send winnerPictureId and loserPictureId

        mImageDuelView.showProgressDialog();
        if (imagesCall == null){
            imagesCall = mApiInterface.getDuelImages(CATEGORY);
            imagesCall.enqueue(new Callback<List<ImageDuelModel>>() {
                @Override
                public void onResponse(Call<List<ImageDuelModel>> call, Response<List<ImageDuelModel>> response) {
                    if (response.isSuccessful()){
                        duelImage = (ArrayList<ImageDuelModel>) response.body();
                        mImageDuelView.onImagesAccessed();
                        mImageDuelView.showData(duelImage);
//                        RxBus.swipeActionObservable().subscribe(new Consumer<SwipedItemParams>() {
//                            @Override
//                            public void accept(SwipedItemParams swipedItemParams) {
//                               viewHolderAdapterPosition = swipedItemParams.getViewholderAdapterPosition();
//                               swipeDirection = swipedItemParams.getDirection();
//                               duelImage.get(viewHolderAdapterPosition);
//                                if (swipeDirection == LEFT){
//                                }
//                               duelImage.get(viewHolderAdapterPosition);
//                            }
//                        });
                    }
                }
                @Override
                public void onFailure(Call<List<ImageDuelModel>> call, Throwable t) {

                }
            });
        }

    }

    public void onImageChosen(int winnerId, int looserId) {

    }
}
