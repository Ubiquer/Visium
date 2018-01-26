package com.example.arek.visium.screens.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.arek.visium.R;
import com.example.arek.visium.screens.image_duel.ImageDuelActivity;
import com.example.arek.visium.screens.image_selection.ImageSelectionActivity;
import com.example.arek.visium.screens.rankings.RankingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by arek on 1/24/2018.
 */

public class MenuFragment extends Fragment {

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

    OnMenuOptionClickedListener menuListener;

    public interface OnMenuOptionClickedListener{
        void navigateToImageDuelActivity();
        void navigateToRankingsActivity();
        void navigateToCompetitionActivity();
    }

    public MenuFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            menuListener = (OnMenuOptionClickedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OnImageClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.rankings_button)
    public void navigateToRankingsActivity(){
        menuListener.navigateToRankingsActivity();
    }
    @OnClick(R.id.evaluation_button)
    public void navigateToImageDuelActivity(){
        menuListener.navigateToImageDuelActivity();
    }
    @OnClick(R.id.competition_button)
    public void navigateToCompetitionActivity(){
        menuListener.navigateToCompetitionActivity();
    }

}
