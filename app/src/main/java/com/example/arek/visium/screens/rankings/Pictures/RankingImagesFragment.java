package com.example.arek.visium.screens.rankings.Pictures;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arek.visium.R;
import com.example.arek.visium.dao.RankingImageByCategory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 10/28/2017.
 */

public class RankingImagesFragment extends Fragment implements RankingImagesFragmentView {

    private RankingImagesFragmentPresenter presenter;
    private RankingImagesRecyclerAdapter adapter;
    @BindView(R.id.ranking_images_rv)
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int resId;

    @Override
    public void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    public void onStop() {
        presenter.onDetach();
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RankingImagesFragmentPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking_images, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

    }

    @Override
    public void showData(ArrayList<RankingImageByCategory> rankingImagesByCategories) {
        initRecyclerView();
        adapter.setData(rankingImagesByCategories);
    }

    private void initRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RankingImagesRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }
}
