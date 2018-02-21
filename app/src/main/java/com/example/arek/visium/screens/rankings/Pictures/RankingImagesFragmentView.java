package com.example.arek.visium.screens.rankings.Pictures;

import com.example.arek.visium.dao.RankingImageByCategory;

import java.util.ArrayList;

/**
 * Created by arek on 10/28/2017.
 */

public interface RankingImagesFragmentView {

    void showData(ArrayList<RankingImageByCategory> rankingImagesByCategories);

}
