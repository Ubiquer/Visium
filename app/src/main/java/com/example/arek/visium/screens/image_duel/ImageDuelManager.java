package com.example.arek.visium.screens.image_duel;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.arek.visium.R;
import com.example.arek.visium.model.ImageDuelModel;
import com.example.arek.visium.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arek on 10/5/2017.
 */

public class ImageDuelManager {

    private ApiInterface mApiInterface;
    private Retrofit mRetrofit;
    ImageDuelActivity mImageDuelActivity;
    Call<List<ImageDuelModel>> imagesCall;
    private static final String CATEGORY = "Samochody";
    private ImageDuelViewAdapter imageDuelViewAdapter;
    private ArrayList<ImageDuelModel> duelImage;
    private int mDisplayHeight;
    private int mDisplayWidth;
    RecyclerView mRecyclerView;
    Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private final String rightColorCode = String.valueOf(R.string.rightColorCode);
    private final String leftColorCode = String.valueOf(R.string.leftColorCode);
    private ImageDuelViewAdapter mRecyclerAdapter;

    public ImageDuelManager(ApiInterface mApiInterface, Retrofit retrofit, Context context, int displayHeight, int displayWidth) {
        this.mApiInterface = mApiInterface;
        this.mRetrofit = retrofit;
        this.mDisplayHeight = displayHeight;
        this.mDisplayWidth = displayWidth;
        this.mContext = context;
    }

    public void onAttach(ImageDuelActivity imageDuelActivity){
        this.mImageDuelActivity = imageDuelActivity;
        getDuelImages();
    }

    public void onStop(){
        this.mImageDuelActivity = null;
    }

    private void getDuelImages(){

        mImageDuelActivity.showProgressDialog();
        if (imagesCall == null){
            imagesCall = mApiInterface.getDuelImages(CATEGORY);
            imagesCall.enqueue(new Callback<List<ImageDuelModel>>() {
                @Override
                public void onResponse(Call<List<ImageDuelModel>> call, Response<List<ImageDuelModel>> response) {
                    if (response.isSuccessful()){
                        mImageDuelActivity.onImagesAccessed();
                        duelImage = (ArrayList<ImageDuelModel>) response.body();
                        imageDuelViewAdapter = new ImageDuelViewAdapter(duelImage, mDisplayHeight, mDisplayWidth);
                        mImageDuelActivity.setRecycylerAdapter(imageDuelViewAdapter);
//                        mRecyclerView.setAdapter(imageDuelViewAdapter);
//                        mRecyclerAdapter.notifyDataSetChanged();
                        mImageDuelActivity.initSwipeHelper();
                    }
                }

                @Override
                public void onFailure(Call<List<ImageDuelModel>> call, Throwable t) {

                }
            });
        }

    }

    private void initSwipeHelper(){

        ItemSwipeHelper itemSwipeHelper = new ItemSwipeHelper(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, mRecyclerAdapter, mContext,rightColorCode, leftColorCode);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(itemSwipeHelper);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

//    private void initRecyclerView(){
//
//        mLinearLayoutManager = new LinearLayoutManager(mContext){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//    }

}
