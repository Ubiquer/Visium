package com.example.arek.visium.screens.rankings.Photographers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arek.visium.R;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.dao.Photographer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 10/28/2017.
 */

public class PhotographersFragment extends Fragment implements PhotographersFragmentView{

    private PhotographersFragmentPresenter photographersFragmentPresenter;
    private PhotographersRecyclerAdapter adapter;
    @BindView(R.id.photographers_rv)
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onStart() {
        photographersFragmentPresenter.onAttach(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        photographersFragmentPresenter.onDetach();
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photographersFragmentPresenter = new PhotographersFragmentPresenter(((VisiumApplication) getActivity().getApplication()).getVisiumService(),
                ((VisiumApplication) getActivity().getApplication()).getRetrofit());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photographers, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void showPhotographersData(ArrayList<Photographer> photographersList) {
        initRecyclerView();
        adapter.setData(photographersList);
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){

        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PhotographersRecyclerAdapter();
        recyclerView.setAdapter(adapter);

    }

}
