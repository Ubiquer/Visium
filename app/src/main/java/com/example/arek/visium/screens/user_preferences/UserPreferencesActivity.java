package com.example.arek.visium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.adapters.UserPreferencesViewAdapter;
import com.example.arek.visium.model.UserPreferencesWithImage;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserPreferencesActivity extends Activity {

    @BindView(R.id.preferences_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.btn_confirm_preferences)
    public Button confirmButton;
    @BindView(R.id.num_of_categories)
    public TextView numOfCategories;

    private ApiInterface mApiInterface;
    GestureDetectorCompat gestureDetector;
    UserPreferencesViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Context context;
    private Intent baseOptionsActivityIntent;
    private static final int itemsInRowNumber = 3;
    ArrayList<UserPreferencesWithImage> userPreferencesWithImages;
//    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_start);
        ButterKnife.bind(this);
        mApiInterface = ApiAdapter.getAPIService();
        initRecyclerView();
        context = getApplicationContext();
    }

    private void initRecyclerView(){

        recyclerViewLayoutManager = new MyGridLayoutManager(context, itemsInRowNumber);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferencesModels();
    }

    private void loadPreferencesModels(){

       mApiInterface.getUserPreferences().enqueue(new Callback<List<UserPreferencesWithImage>>() {
           @Override
           public void onResponse(Call<List<UserPreferencesWithImage>> call, Response<List<UserPreferencesWithImage>> response) {

               if(response.isSuccessful()) {

                  userPreferencesWithImages = (ArrayList<UserPreferencesWithImage>) response.body();
                  recyclerViewAdapter = new UserPreferencesViewAdapter(userPreferencesWithImages, getApplicationContext());
                  recyclerView.setAdapter(recyclerViewAdapter);
//                  commitToRealm();
               }
           }

           @Override
           public void onFailure(Call<List<UserPreferencesWithImage>> call, Throwable t) {

               Toast.makeText(getBaseContext(), "model received" + t, Toast.LENGTH_SHORT).show();
           }
       });

    }
//    private void commitToRealm(){

//        ArrayList<String> mCategoriesList = new ArrayList<>();
//        for (UserPreferencesWithImage userPreferencesWithImage : userPreferencesWithImages){
//
//            mCategoriesList.add(userPreferencesWithImage.getCategoryName());
//        }
//        realm.beginTransaction();
//        ListOfCategories categoriesList = realm.createObject(ListOfCategories.class);
//        categoriesList.setCategoriesList(mCategoriesList);
//        realm.commitTransaction();
//    }

    @OnClick(R.id.btn_confirm_preferences)
    public void confirmPreferences(){

        baseOptionsActivityIntent = new Intent(getBaseContext(), MenuActivity.class);
        recyclerViewAdapter.getSelectedItems();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserPreferencesActivity.this);
        mBuilder.setTitle("Chosen preferences");
        mBuilder.setMessage("Number of preferences: " + recyclerViewAdapter.getItemCount());
        mBuilder.setMessage("Preferences: " +  recyclerViewAdapter.getSelectedItems());
        mBuilder.setMessage("Number of preferences: " +
                recyclerViewAdapter.getSelectedItems().size() +
                "\nChosen preferences: " +
                recyclerViewAdapter.getSelectedItems() +
                "\nDo you confirm chosen preferences?").setPositiveButton("YES",
                (dialog, which) -> {
                   startActivity(baseOptionsActivityIntent);
                }).setNegativeButton("NO", ((dialog, which) -> mBuilder.setCancelable(true)));
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

}


