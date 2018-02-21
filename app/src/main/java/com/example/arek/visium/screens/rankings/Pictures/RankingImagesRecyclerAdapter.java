package com.example.arek.visium.screens.rankings.Pictures;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arek.visium.R;
import com.example.arek.visium.dao.RankingImageByCategory;
import com.example.arek.visium.rest.ApiKeys;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 10/28/2017.
 */

public class RankingImagesRecyclerAdapter extends RecyclerView.Adapter<RankingImagesRecyclerAdapter.RankingImagesViewHolder>{

    private ArrayList<RankingImageByCategory> rankingImagesList;
    private Context context;
    private final static int FADE_DURATION = 1000;
    private int rankingPosition;

    public RankingImagesRecyclerAdapter() {
    }

    @Override
    public RankingImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_ranking_row, parent, false);
        return new RankingImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingImagesViewHolder holder, int position) {

        final RankingImageByCategory rankingImageByCategory = rankingImagesList.get(position);
        Picasso.with(holder.rankingImage.getContext())
                .load(ApiKeys.BASE_URL + rankingImageByCategory.getImagePath())
                .resize(350,350)
                .into(holder.rankingImage);
        String date = rankingImageByCategory.getDateOfUpload();
        holder.rankingPoints.setText(String.valueOf(rankingImageByCategory.getRankingPoints()));
        holder.uploadDate.setText(date.substring(0,10));
        holder.rankingCategory.setText(rankingImageByCategory.getCategory());
        holder.rankingPosition.setText(String.valueOf(rankingImageByCategory.getRankingPosition()));
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return rankingImagesList.size();
    }

    public class RankingImagesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ranking_image)
        ImageView rankingImage;
        @BindView(R.id.ranking_points)
        TextView rankingPoints;
        @BindView(R.id.date_of_upload)
        TextView uploadDate;
        @BindView(R.id.ranking_category)
        TextView rankingCategory;
        @BindView(R.id.ranking_position)
        TextView rankingPosition;

        public RankingImagesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(ArrayList<RankingImageByCategory> rankingImagesList){
        this.rankingImagesList = rankingImagesList;
        notifyDataSetChanged();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}
