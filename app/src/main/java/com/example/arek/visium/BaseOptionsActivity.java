package com.example.arek.visium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseOptionsActivity extends AppCompatActivity {

    private static final int SELECTION_REQUEST_CODE = 6465;

    @BindView(R.id.app_logo)
    ImageView logoImage;
    @BindView(R.id.competition_textview)
    TextView competitionTextView;
    @BindView(R.id.evaluatiion_textview)
    TextView evaluationTextView;
    @BindView(R.id.rankings_textview)
    TextView rankingsTextView;

    private Intent imageSelectionActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_options);
        ButterKnife.bind(this);

        logoImage.setImageResource(R.drawable.logo1);

        competitionTextView.setOnClickListener(v -> {

            imageSelectionActivity = new Intent(getApplicationContext(), TestActivity.class);
            startActivityForResult(imageSelectionActivity, SELECTION_REQUEST_CODE);

        });



    }

}
