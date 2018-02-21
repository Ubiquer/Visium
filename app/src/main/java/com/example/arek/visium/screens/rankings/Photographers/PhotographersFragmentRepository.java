package com.example.arek.visium.screens.rankings.Photographers;

import com.example.arek.visium.dao.Photographer;

import java.util.ArrayList;

/**
 * Created by arek on 11/30/2017.
 */

public interface PhotographersFragmentRepository {

    void downloadPhotographers(OnPhotographersAccessed onPhotographersAccessed);

    interface OnPhotographersAccessed{
        void getPhotographers(ArrayList<Photographer> photographers);
    }

}
