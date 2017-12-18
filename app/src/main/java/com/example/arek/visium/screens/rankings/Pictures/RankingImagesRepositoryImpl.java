package com.example.arek.visium.screens.rankings.Pictures;

import android.content.Context;
import android.util.Log;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.model.RankingImageByCategory;
import com.example.arek.visium.rest.VisiumService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arek on 11/30/2017.
 */

public class RankingImagesRepositoryImpl implements RankingImagesRepository {

    private VisiumService visiumService;
    private Retrofit retrofit;
    private Call<List<RankingImageByCategory>> rankingImagesCall;
    private ArrayList<RankingImageByCategory> rankingImages;
    private Context context;

    public RankingImagesRepositoryImpl() {
        rankingImagesCall = null;
        context = VisiumApplication.getContext();
        retrofit = ((VisiumApplication) context).getRetrofit();
        visiumService = ((VisiumApplication) context).getVisiumService();
    }

    @Override
    public void downloadRankingImages(OnRankingImagesDownloaded onRankingImagesDownloaded) {

        if (rankingImagesCall == null){
            rankingImagesCall = visiumService.getAllImagesFromCategoryByRanking();
            rankingImagesCall.enqueue(new Callback<List<RankingImageByCategory>>() {
                @Override
                public void onResponse(Call<List<RankingImageByCategory>> call, Response<List<RankingImageByCategory>> response) {
                    if (response.isSuccessful()){
                        rankingImages = (ArrayList<RankingImageByCategory>) response.body();
                        onRankingImagesDownloaded.getRankingImages(rankingImages);
//                        rankingImagesCall = null;
                    }else {
                        Log.d("Went wrong: ", response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<RankingImageByCategory>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
