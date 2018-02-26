package com.example.arek.visium.screens.menu;

import android.Manifest;
import android.accounts.Account;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.R;
import com.example.arek.visium.UserStorage;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.dependency_injection.screens.menu_di.DaggerMenuActivityComponent;
import com.example.arek.visium.dependency_injection.screens.menu_di.MenuActivityComponent;
import com.example.arek.visium.dependency_injection.screens.menu_di.MenuActivityModule;
import com.example.arek.visium.screens.image_selection.ImageSelectionActivity;
import com.example.arek.visium.screens.image_duel.ImageDuelActivity;
import com.example.arek.visium.screens.login.LoginActivity;
import com.example.arek.visium.screens.menu.account_fragment.AccountFragmentPresenter;
import com.example.arek.visium.screens.rankings.RankingsActivity;
import com.example.arek.visium.screens.menu.account_fragment.AccountFragment;
import com.example.arek.visium.screens.user_pictures.UserPicturesFragment;

import javax.inject.Inject;

public class MenuActivity extends AppCompatActivity
        implements MenuActivityView, NavigationView.OnNavigationItemSelectedListener, AccountFragment.Callback, MenuFragment.OnMenuOptionClickedListener{

    //**TODO: Implement MVP for this screen.

    private static final int SELECTION_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_REQUEST = 1;
    private boolean permissionGranted;
    private UserStorage userStorage;

    private Intent competitionActivity;
    private Intent imageDuelActivity;
    private Intent rankingsActivity;
    private NavigationView navigationView;
    private static final int CAMERA_PIC_REQUEST = 2;
    private FragmentManager manager;
    private MenuActivityComponent component;

    @Inject
    MenuActivityView view;

    @Inject
    MenuActivityPresenter presenter;

    @Inject
    AccountFragmentPresenter accountFragmentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
//        ButterKnife.bind(this);

        component = DaggerMenuActivityComponent.builder()
                .menuActivityModule(new MenuActivityModule(this))
                .visiumApplicationComponent(VisiumApplication.get(this).component())
                .build();
        component.injectMenuActivity(this);

        if (!presenter.sessionTokenActive()){
            goToLogin();
            return;
        }

        if(savedInstanceState == null){
            MenuFragment menuFragment = new MenuFragment();
            manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.container, menuFragment)
                    .commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> requestPermission());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView drawerNameTextView = headerView.findViewById(R.id.drawerNameTextView);
        TextView drawerEmailTextView = headerView.findViewById(R.id.drawerEmailTextView);

        drawerNameTextView.setText(R.string.test_username);
        drawerEmailTextView.setText("test");
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            goToLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout){
            goToLogin();
        } else if (id == R.id.nav_subscribe){
            showAccountFragment();
        } else if (id == R.id.nav_user_pictures){
            showFragment(new UserPicturesFragment());
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
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
            Toast.makeText(getApplicationContext(), R.string.camera_permission_denied, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void navigateToImageDuelActivity() {
        imageDuelActivity = new Intent(this, ImageDuelActivity.class);
        startActivity(imageDuelActivity);
    }

    @Override
    public void navigateToRankingsActivity() {
        rankingsActivity = new Intent(this, RankingsActivity.class);
        startActivity(rankingsActivity);
    }

    @Override
    public void navigateToCompetitionActivity() {
        competitionActivity = new Intent(this, ImageSelectionActivity.class);
        startActivity(competitionActivity);
    }

    @Override
    protected void onDestroy() {
        presenter.deleteSessionToken();
        super.onDestroy();
    }

    private void showAccountFragment(){
       Bundle args = new Bundle();
       args.putStringArrayList("preferences_list", accountFragmentPresenter.getPreferences());
       AccountFragment accountFragment = new AccountFragment();
       showFragment(accountFragment);
       accountFragment.setArguments(args);
    }

}
