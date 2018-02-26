package com.example.arek.visium.screens.rankings.Photographers;

import com.example.arek.visium.dao.Photographer;
import com.example.arek.visium.rest.VisiumService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by arek on 10/28/2017.
 */

public class PhotographersFragmentPresenter implements PhotographersFragmentRepository.OnPhotographersAccessed {

    private VisiumService visiumService;
    private Retrofit retrofit;
    private Call<List<Photographer>> photographersCall;
    private ArrayList<Photographer> photographersList;
    private PhotographersFragmentView view;
    private PhotographersFragmentRepositoryImpl repository;

    public PhotographersFragmentPresenter(VisiumService visiumService, Retrofit retrofit) {
        this.visiumService = visiumService;
        this.retrofit = retrofit;
    }

    public void onAttach(PhotographersFragmentView photographersFragmentView){
      this.view = photographersFragmentView;
      photographersCall = null;
      downloadPhotographers();
    }

    public void onDetach() {
        this.view = null;
    }

    private void downloadPhotographers() {
        repository = new PhotographersFragmentRepositoryImpl();
        repository.downloadPhotographers(this);
    }

    @Override
    public void getPhotographers(ArrayList<Photographer> photographers) {
        view.showPhotographersData(photographers);
    }
}
