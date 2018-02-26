package com.example.arek.visium.screens.menu.account_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.arek.visium.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 10/2/2017.
 */

//TODO: on rotation data store

public class AccountFragment extends Fragment{

    ArrayList<String> myPreferences;
    private GridLayoutManager gridLayoutManager;
    @BindView(R.id.empty)
    TextView emptyView;
    @BindView(R.id.rv_preferences)
    RecyclerView recyclerView;
    private CategoriesAdapter adapter;

    public interface Callback{
        void goToUserPictures();
    }

    private Callback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        myPreferences = args.getStringArrayList("preferences_list");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerView();
    }

    private void initRecyclerView(){
        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (myPreferences.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            adapter = new CategoriesAdapter();
            adapter.setData(myPreferences);
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        }
    }

}
