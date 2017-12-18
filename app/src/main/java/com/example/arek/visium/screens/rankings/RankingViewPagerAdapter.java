package com.example.arek.visium.screens.rankings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arek.visium.screens.rankings.Photographers.PhotographersFragment;
import com.example.arek.visium.screens.rankings.Pictures.RankingImagesFragment;

/**
 * Created by arek on 10/27/2017.
 */

public class RankingViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String PHOTOGRAPHERS = "Photographers";
    private final String BEST_PICTURES = "Best pics";

    public RankingViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if (position == 0){
            fragment = new RankingImagesFragment();
        }else if (position == 1){
            fragment = new PhotographersFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;

        if (position == 0){

            title = BEST_PICTURES;
        }
        else if (position == 1)
        {
            title = PHOTOGRAPHERS;
        }

        return title;
    }
}
