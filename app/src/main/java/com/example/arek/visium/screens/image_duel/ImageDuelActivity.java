package com.example.arek.visium.screens.image_duel;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Display;

import com.example.arek.visium.R;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.model.ImageDuelModel;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;
import com.example.arek.visium.rest.Services.BackgroundService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDuelActivity extends AppCompatActivity implements ImageDuelView {

    private static final String TAG = "DISPLAY_METRICS" ;
    @BindView(R.id.duel_rv)
    RecyclerView mRecyclerView;

    boolean isImageChosen;
    private Context mContext;
    private ApiInterface mApiInterface;
    private final String CATEGORY = "Samochody";
    private ArrayList<ImageDuelModel> duelImage;
    private ImageDuelViewAdapter mRecyclerAdapter;
    private int displayHeight;
    private int displayWidth;
    private LinearLayoutManager mLinearLayoutManager;
    private ItemSwipeHelper itemSwipeHelper;
    private final String rightColorCode = "#388E3C";
    private final String leftColorCode = "#D32F2F";
    BackgroundService service;
    private int mDirection;
    private ProgressDialog progressDialog;
    private ImageDuelManager imageDuelManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_duel);
        ButterKnife.bind(this);
        screenMetrics();

        imageDuelManager = new ImageDuelManager(((VisiumApplication) getApplication()).getApiInterface(),
                ((VisiumApplication) getApplication()).getRetrofit(), this, displayHeight, displayWidth);
//        screenMetrics();
//        mApiInterface = ApiAdapter.getAPIService();
//        mContext = getApplicationContext();
//        initRecyclerView();
    }

    private void initRecyclerView(){

        mLinearLayoutManager = new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    protected void onStart() {

        super.onStart();
        initRecyclerView();
        imageDuelManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        imageDuelManager.onStop();
    }

    private void loadDuelImage(){

        mApiInterface.getDuelImages(CATEGORY).enqueue(new Callback<List<ImageDuelModel>>() {
            @Override
            public void onResponse(Call<List<ImageDuelModel>> call, Response<List<ImageDuelModel>> response) {

                if (response.isSuccessful()){
                    duelImage = (ArrayList<ImageDuelModel>) response.body();
                    mRecyclerAdapter = new ImageDuelViewAdapter(duelImage, displayHeight, displayWidth);
                    mRecyclerView.setAdapter(mRecyclerAdapter);
                    mRecyclerAdapter.notifyDataSetChanged();
//                    initSwipeHelper();
                    //zrobic callback z id przesunietego obrazka

//                    sendWinner();
                }
            }

            @Override
            public void onFailure(Call<List<ImageDuelModel>> call, Throwable t) {

                Log.d("Failed: ", t.toString());
            }
        });
    }

//    private void loadDuelImage(){
//
//        Intent intent = new Intent(ImageDuelActivity.this, BackgroundService.class);
//        startService(intent);
//
//    }

    void sendWinner(){

    }

    private void screenMetrics(){
        Display display = getWindowManager().getDefaultDisplay();
        String displayName = display.getName();
        Log.i(TAG, "displayName  = " + displayName);
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;
    }

    public void initSwipeHelper(){
        ItemSwipeHelper itemSwipeHelper = new ItemSwipeHelper(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, mRecyclerAdapter, mContext,rightColorCode, leftColorCode);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(itemSwipeHelper);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "Loading pictures...", null);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void setRecycylerAdapter(ImageDuelViewAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onImagesAccessed() {
        progressDialog.dismiss();
    }
}
