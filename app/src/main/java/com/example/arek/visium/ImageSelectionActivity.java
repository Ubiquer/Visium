package com.example.arek.visium;

import android.content.Context;
import android.content.Intent;
import android.os.ConditionVariable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.action;
import static android.R.attr.type;

public class ImageSelectionActivity extends AppCompatActivity {

    @BindView(R.id.image_carousel_recyclerview)
    RecyclerView carouselRecyclerView;
    @BindView(R.id.carousel_selected_image)
    ImageView carouselSelectedImage;
    @BindView(R.id.btn_confirm_duel_image)
    Button confirmButton;
    @BindView(R.id.fab_button)
    FloatingActionButton myFab;
    private ImageSelectionViewAdapter adapter;
    Context mContext;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        ButterKnife.bind(this);

//        Intent intent = getIntent();
//        String action = intent.getAction();
//        String type = intent.getType();
//
//        if (Intent.ACTION_SEND.equals(action) && type != null) {
//            if ("text/plain".equals(type)) {
//                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
//                if (sharedText != null) {
//                    // Update UI to reflect text being shared
//                }
//            }
//        }

//        initAlertDialog();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

//    private void initAlertDialog(){
//
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getBaseContext());
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View content = inflater.inflate(R.layout.content_start, null);
//        mBuilder.setView(content);
//        mRecyclerView = (RecyclerView) content.findViewById(R.id.preferences_recyclerview);
//
//
//    }

}
