package com.example.arek.visium.rest.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.arek.visium.model.DuelImage;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.VisiumService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by arek on 9/21/2017.
 */

public class BackgroundService extends IntentService {

    private final String CATEGORY = "Samochody";
    private VisiumService mVisiumService;
    Response<List<DuelImage>> result;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        mVisiumService = ApiAdapter.getAPIService();
        Call<List<DuelImage>> call = mVisiumService.getDuelImages(CATEGORY);

        try {
            result = call.execute();
            Log.i("Retrofit", "Success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response<List<DuelImage>> getResult() {
        return result;
    }
}
