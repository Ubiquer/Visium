package com.example.arek.visium;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arek.visium.image_duel.ImageDuelActivity;
import com.example.arek.visium.image_selection.ImageSelectionActivity;
import com.example.arek.visium.screens.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseOptionsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
    private Intent imageDuelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_options);
        ButterKnife.bind(this);

        UserStorage userStorage = ((VisiumApplication)getApplication()).getUserStorage();
        if (userStorage.hasToLogin()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        competitionTextView.setOnClickListener(v -> {
            imageSelectionActivity = new Intent(getApplicationContext(), ImageSelectionActivity.class);
            startActivityForResult(imageSelectionActivity, SELECTION_REQUEST_CODE);
        });
    }

    @OnClick(R.id.evaluatiion_textview)
    public void navigateToImageDuelActivity(){
        imageDuelActivity = new Intent(getBaseContext(), ImageDuelActivity.class);
        startActivity(imageDuelActivity);
    }

    @OnClick(R.id.rankings_textview)
    public void navigateToRankingsActivity(){
        Intent rankingsActivity = new Intent(getBaseContext(), MenuActivity.class);
        startActivity(rankingsActivity);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }
}
