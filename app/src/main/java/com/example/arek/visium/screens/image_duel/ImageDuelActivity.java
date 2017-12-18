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
import com.example.arek.visium.model.DuelImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDuelActivity extends AppCompatActivity implements ImageDuelView, ItemSwipeHelper.ImageChooseListener {

    private static final String TAG = "DISPLAY_METRICS" ;
    @BindView(R.id.duel_rv)
    RecyclerView mRecyclerView;
    private Context mContext;
    private final String CATEGORY = "Samochody";
    private ImageDuelViewAdapter mRecyclerAdapter;
    private int displayHeight;
    private int displayWidth;
    private LinearLayoutManager mLinearLayoutManager;
    private ItemSwipeHelper itemSwipeHelper;
    private final String rightColorCode = "#388E3C";
    private final String leftColorCode = "#D32F2F";
    private ProgressDialog progressDialog;
    private ImageDuelPresenter imageDuelPresenter;
    private ImageDuelViewAdapter imageDuelViewAdapter;
    private int winnerPosition;
    private int loserPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_duel);
        ButterKnife.bind(this);
        screenMetrics();
        imageDuelPresenter = new ImageDuelPresenter(((VisiumApplication) getApplication()).getVisiumService(),
                ((VisiumApplication) getApplication()).getRetrofit());
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
        imageDuelViewAdapter = new ImageDuelViewAdapter();
        mRecyclerView.setAdapter(imageDuelViewAdapter);
        initSwipeHelper();
    }
    @Override
    protected void onStart() {
        super.onStart();
        imageDuelPresenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        imageDuelPresenter.onDetach();
        super.onStop();
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
        itemSwipeHelper = new ItemSwipeHelper(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, mRecyclerAdapter, mContext,rightColorCode, leftColorCode, this);
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
    public void showData(ArrayList<DuelImage> duelImagesList) {
        initRecyclerView();
        onImagesAccessed();
        imageDuelViewAdapter.setData(duelImagesList, displayHeight, displayWidth);
    }

    @Override
    public void onImagesAccessed() {
//        progressDialog.dismiss();
    }

    @Override
    public void onImageChosen(int position, boolean winner) {

        if (winner){
            this.winnerPosition = position;
            if (winnerPosition == 0){
                loserPosition = 1;
            }else{
                loserPosition = 0;
            }
        }else{
            this.loserPosition = position;
            if (loserPosition == 0){
                winnerPosition = 1;
            }else {
                winnerPosition = 0;
            }
        }
        ArrayList<DuelImage> images = imageDuelViewAdapter.getImages();
        imageDuelPresenter.onImageChosen(images.get(winnerPosition).getPictureId(), images.get(loserPosition).getPictureId());
    }
}
