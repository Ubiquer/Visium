package com.example.arek.visium;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.arek.visium.model.UserPreferencesImageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserPreferencesActivity extends Activity implements RecyclerView.OnItemTouchListener,View.OnClickListener {


    @BindView(R.id.preferences_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.confirmation_button)
    public Button confirmButton;
    @BindView(R.id.num_of_categories)
    public TextView numOfCategories;

    ActionMode actionMode;
    int itemCount;
    GestureDetectorCompat gestureDetector;
    UserPreferencesViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Context context;
    private static final int itemsInRowNumber = 3;
    ArrayList<UserPreferencesImageModel> imageArrayList;

    private final Integer image_ids[] = {R.drawable.architecture, R.drawable.bird, R.drawable.moto,
                               R.drawable.nature, R.drawable.moto1, R.drawable.style, R.drawable.midget};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_start);

        ButterKnife.bind(this);

        imageArrayList = pupulateData();
        context = getApplicationContext();

        recyclerViewLayoutManager = new MyGridLayoutManager(context, itemsInRowNumber);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new UserPreferencesViewAdapter(context, imageArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(this);

//        numOfCategories.setText((CharSequence) recyclerViewAdapter.getSelectedItem());

    }


    private ArrayList<UserPreferencesImageModel> pupulateData(){

        ArrayList<UserPreferencesImageModel> imageModels = new ArrayList<>();

        for (int i=0; i<image_ids.length; i++){

            UserPreferencesImageModel imageModel = new UserPreferencesImageModel();
            imageModel.setImageId(image_ids[i]);
            imageModels.add(imageModel);

        }

        return imageModels;

    }



    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onClick(View v) {

        numOfCategories.setText((CharSequence) recyclerViewAdapter.getSelectedItem());

    }

}


