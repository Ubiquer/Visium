package com.example.arek.visium.screens.user_preferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.dependency_injection.screens.user_preferences_di.DaggerUserPreferencesActivityComponent;
import com.example.arek.visium.dependency_injection.screens.user_preferences_di.UserPreferencesActivityComponent;
import com.example.arek.visium.dependency_injection.screens.user_preferences_di.UserPreferencesActivityModule;
import com.example.arek.visium.screens.menu.MenuActivity;
import com.example.arek.visium.MyGridLayoutManager;
import com.example.arek.visium.R;
import com.example.arek.visium.model.UserPreferencesWithImage;
import com.example.arek.visium.rest.VisiumService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserPreferencesActivity extends Activity implements UserPreferencesView {

    @BindView(R.id.preferences_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.btn_confirm_preferences)
    public Button confirmButton;
    @BindView(R.id.num_of_categories)
    public TextView numOfCategories;

    private VisiumService mVisiumService;
    UserPreferencesRecyclerAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Context context;
    private Intent menuActivityIntent;
    private static final int columnsNumber = 3;
    private UserPreferencesPresenterImpl userPreferencesPresenterImpl;
    private ArrayList<Integer> chosenPreferences;
    int resId;

    @Inject
    UserPreferencesPresenter presenter;

    private UserPreferencesActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_start);
        ButterKnife.bind(this);

        component = DaggerUserPreferencesActivityComponent.builder()
                .userPreferencesActivityModule(new UserPreferencesActivityModule(this))
                .visiumApplicationComponent(VisiumApplication.get(this).component())
                .build();

        component.injectUserPreferencesActivity(this);
        presenter.onCreate();
        initRecyclerView();

    }

    private void initRecyclerView(){
        resId = R.anim.grid_layout_animation_from_bottom;
        recyclerViewLayoutManager = new MyGridLayoutManager(context, columnsNumber);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new UserPreferencesRecyclerAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        userPreferencesPresenterImpl.onAttach(this);
    }

    @Override
    protected void onStop() {
//        userPreferencesPresenterImpl.onDetach();
        super.onStop();
        finish();
    }

    @OnClick(R.id.btn_confirm_preferences)
    public void confirmPreferences(){
        menuActivityIntent = new Intent(getBaseContext(), MenuActivity.class);
        List selectedPreferences = recyclerViewAdapter.getSelectedItems();
        Log.d("Message", selectedPreferences.toString());
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserPreferencesActivity.this);
        mBuilder.setTitle("Chosen preferences");
        mBuilder.setMessage("Number of preferences: " + recyclerViewAdapter.getItemCount());
        mBuilder.setMessage("Preferences: " +  selectedPreferences);
        mBuilder.setMessage("Number of preferences: " +
                selectedPreferences.size() +
                "\nChosen preferences: " +
                selectedPreferences +
                "\nDo you confirm chosen preferences?").setPositiveButton("YES",
                (dialog, which) -> {
                   startActivity(menuActivityIntent);
                    chosenPreferences = recyclerViewAdapter.getPreferences();
                    userPreferencesPresenterImpl.commitSelectedPreferencesToRealm(selectedPreferences);
                    userPreferencesPresenterImpl.sendPreferencesToDB(chosenPreferences);
                }).setNegativeButton("NO", ((dialog, which) -> mBuilder.setCancelable(true)));
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void showData(ArrayList<UserPreferencesWithImage> userPreferencesWithImages) {
        recyclerViewAdapter.setData(userPreferencesWithImages);
        recyclerView.setAdapter(recyclerViewAdapter);
        final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onPreferencesDownloadFailed(String message) {
        Toast.makeText(getBaseContext(), getString(R.string.loading_preferences_failure) + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure(String message) {
        Toast.makeText(getBaseContext(), getString(R.string.api_connection_failure) + message, Toast.LENGTH_SHORT).show();
    }
}


