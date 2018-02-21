package com.example.arek.visium.screens.rankings.Photographers;

import android.content.Context;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.dao.Photographer;
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

public class PhotographersFragmentRepositoryImpl implements PhotographersFragmentRepository{

    private VisiumService visiumService;
    private Retrofit retrofit;
    private Context context;
    private Call<List<Photographer>> photographersCall;
    private ArrayList<Photographer> photographersList;

    public PhotographersFragmentRepositoryImpl() {

        context = VisiumApplication.getContext();
        retrofit = ((VisiumApplication) context).getRetrofit();
        visiumService = ((VisiumApplication) context).getVisiumService();

    }

    @Override
    public void downloadPhotographers(OnPhotographersAccessed onPhotographersAccessed) {

        if (photographersCall == null){

            photographersCall = visiumService.getAllPhotographers();
            photographersCall.enqueue(new Callback<List<Photographer>>() {
                @Override
                public void onResponse(Call<List<Photographer>> call, Response<List<Photographer>> response) {
                    if (response.isSuccessful()){
                        photographersList = (ArrayList<Photographer>) response.body();
                        onPhotographersAccessed.getPhotographers(photographersList);
                        photographersCall = null;
                    }
                }

                @Override
                public void onFailure(Call<List<Photographer>> call, Throwable t) {
                    t.printStackTrace();
                    photographersCall = null;
                }
            });

        }

    }
}
