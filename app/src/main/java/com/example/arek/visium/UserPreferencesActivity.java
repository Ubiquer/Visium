package com.example.arek.visium;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
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


public class UserPreferencesActivity extends Activity implements RecyclerView.OnItemTouchListener,View.OnClickListener, android.view.ActionMode.Callback {


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
        recyclerView.addOnItemTouchListener(this);

        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());

//        recyclerView.addOnItemTouchListener(new UserPreferencesViewAdapter.);

//        numOfCategories

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

//    private void creatingObserable() {
//        final Observable<List<String>> listObserable= Observable.just(getNameList());
//        listObserable.subscribe(new Observer<List<String>>() {
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "onCompleted()");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError()",e);
//            }
//
//            @Override
//            public void onNext(List<String> data) {
//                myAdapter.setData(data);
//            }
//        });

//    }

    public void onLongPress(MotionEvent event){

        View view = recyclerView.findChildViewUnder(event.getX(), event.getY());

        if(actionMode != null){
            return;
        }

        actionMode = startActionMode(UserPreferencesActivity.this);
        int idx = recyclerView.getChildAdapterPosition(view);

    }

    private void myToggleSelection(int idx){

        recyclerViewAdapter.toggleSelection(idx);

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

        numOfCategories.setText(recyclerViewAdapter.getItemCount());

    }

    @Override
    public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {

        switch(item.getItemId()){
            case R.id.confirmation_button:
                List<Integer> selectedItemPositions = recyclerViewAdapter.getSelectedItems();
                //TODO: write to user preferences to sharedPreferences
                actionMode.finish();

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode mode) {

        this.actionMode = null;
        recyclerViewAdapter.clearSelections();
    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            numOfCategories.setText(recyclerViewAdapter.getItemCount());
            Log.d("tap", view.toString());
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            // Start the CAB using the ActionMode.Callback defined above
            actionMode = startActionMode(UserPreferencesActivity.this);
            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }

}


