package com.example.arek.visium.screens.menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.R;
import com.example.arek.visium.UserStorage;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.screens.image_selection.ImageSelectionActivity;
import com.example.arek.visium.screens.image_duel.ImageDuelActivity;
import com.example.arek.visium.screens.login.LoginActivity;
import com.example.arek.visium.screens.rankings.RankingsActivity;
import com.example.arek.visium.screens.menu.subscribe.SubscribedFragment;
import com.example.arek.visium.screens.user_pictures.UserPicturesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SubscribedFragment.Callback{

    //TODO: Implement MVP for this screen.

    private static final int SELECTION_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_REQUEST = 1;
    private boolean permissionGranted;

    @BindView(R.id.app_logo)
    ImageView logoImage;
    @BindView(R.id.competition_button)
    Button competitionButton;
    @BindView(R.id.evaluation_button)
    Button evaluationButton;
    @BindView(R.id.rankings_button)
    Button rankingsButton;
    @BindView(R.id.drawer_relative)
    RelativeLayout relativeLayout;
    private UserStorage userStorage;

    private Intent competitionActivity;
    private Intent imageDuelActivity;
    private Intent rankingsActivity;
    private NavigationView navigationView;
    private static final int CAMERA_PIC_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

         userStorage = ((VisiumApplication)getApplication()).getUserStorage();
//        if (userStorage.noSessionToken()){
//            goToLogin();
//            return;
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView drawerNameTextView = (TextView) headerView.findViewById(R.id.drawerNameTextView);
        TextView drawerEmailTextView = (TextView) headerView.findViewById(R.id.drawerEmailTextView);

        drawerNameTextView.setText(R.string.test_username);
        drawerEmailTextView.setText(userStorage.getEmail());
    }

    private void showSnackbar() {
        Snackbar snackbar = Snackbar.make(relativeLayout, "Camera permission denied", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            userStorage.logout();
            goToLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.rankings_button)
    public void navigateToRankingsActivity(){
        rankingsActivity = new Intent(getBaseContext(), RankingsActivity.class);
        startActivity(rankingsActivity);
    }
    @OnClick(R.id.evaluation_button)
    public void navigateToImageDuelActivity(){
        imageDuelActivity = new Intent(getBaseContext(), ImageDuelActivity.class);
        startActivity(imageDuelActivity);
    }
    @OnClick(R.id.competition_button)
    public void navigateToCompetitionActivity(){
        competitionActivity = new Intent(getBaseContext(), ImageSelectionActivity.class);
        startActivity(competitionActivity);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout){
            userStorage.logout();
            goToLogin();
        } else if (id == R.id.nav_subscribe){
            showFragment(new SubscribedFragment());
        } else if (id == R.id.nav_user_pictures){
            showFragment(new UserPicturesFragment());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    @Override
    public void goToUserPictures() {
       MenuItem item = navigationView.getMenu().findItem(R.id.nav_user_pictures);
       item.setChecked(true);
       onNavigationItemSelected(item);
    }

    private void requestPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        }else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

        }else{

            Toast.makeText(getApplicationContext(),"Camera permission denied", Toast.LENGTH_SHORT).show();

        }

    }
}
