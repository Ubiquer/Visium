package com.example.arek.visium.repositories;

import android.content.Context;
import android.widget.Toast;

import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 9/3/2017.
 */

public class UserPreferencesRepository {

    private ArrayList<Integer> preferenceIds;
    private ApiInterface mApiInterface;
    private Context mContext;

    public UserPreferencesRepository(ArrayList<Integer> preferenceIds, Context context) {

        this.preferenceIds = preferenceIds;
        this.mContext = context;

    }


    private void postPreferencesToApi(){

        mApiInterface = ApiAdapter.getAPIService();

        mApiInterface.sendPreferences(preferenceIds).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(mContext, "Server response: "+ response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(mContext, "Failed!!!", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
