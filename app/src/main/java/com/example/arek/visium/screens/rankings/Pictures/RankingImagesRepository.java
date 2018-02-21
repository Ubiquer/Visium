package com.example.arek.visium.screens.rankings.Pictures;

import com.example.arek.visium.dao.RankingImageByCategory;

import java.util.ArrayList;

/**
 * Created by arek on 11/30/2017.
 */

public interface RankingImagesRepository {

    void downloadRankingImages(OnRankingImagesDownloaded onRankingImagesDownloaded);

    interface OnRankingImagesDownloaded{
        void getRankingImages(ArrayList<RankingImageByCategory> rankingImageArrayList);
    }

}
