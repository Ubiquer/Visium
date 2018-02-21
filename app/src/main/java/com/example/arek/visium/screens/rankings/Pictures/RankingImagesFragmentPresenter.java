package com.example.arek.visium.screens.rankings.Pictures;

import com.example.arek.visium.dao.RankingImageByCategory;

import java.util.ArrayList;

/**
 * Created by arek on 10/28/2017.
 */

public class RankingImagesFragmentPresenter implements RankingImagesRepository.OnRankingImagesDownloaded {

    private RankingImagesFragmentView rankingImagesFragmentView;
    private RankingImagesRepositoryImpl repository;

    public void onAttach(RankingImagesFragmentView rankingImagesFragmentView){
        this.rankingImagesFragmentView = rankingImagesFragmentView;
        downloadRankingImages();
    }

    public void onDetach(){
        rankingImagesFragmentView = null;
    }

    public RankingImagesFragmentPresenter() {
        repository = new RankingImagesRepositoryImpl();
    }

    private void downloadRankingImages() {
        repository.downloadRankingImages(this);
    }

    @Override
    public void getRankingImages(ArrayList<RankingImageByCategory> rankingImageArrayList) {
        rankingImagesFragmentView.showData(rankingImageArrayList);
    }
}
