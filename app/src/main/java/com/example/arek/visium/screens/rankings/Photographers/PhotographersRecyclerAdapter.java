package com.example.arek.visium.screens.rankings.Photographers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arek.visium.R;
import com.example.arek.visium.model.Photographer;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 10/28/2017.
 */

public class PhotographersRecyclerAdapter extends RecyclerView.Adapter<PhotographersRecyclerAdapter.PhotographersViewHolder>{

    private ArrayList<Photographer> photographers;

    public PhotographersRecyclerAdapter() {
    }

    @Override
    public PhotographersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photographers_ranking_row, parent, false);
        return new PhotographersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotographersViewHolder holder, int position) {

        final Photographer photographer = photographers.get(position);
//        Picasso.with(holder.avatar.getContext())
//                .load(ApiKeys.BASE_URL + photographer.getAvatarPath())
//                .resize(R.dimen.circularPhotographersAvatarWidth, R.dimen.circularPhotographersAvatarHeight)
//                .into(holder.avatar);
        holder.name.setText(photographer.getName() + " " + photographer.getSurname());
        holder.rankingPoints.setText(String.valueOf(photographer.getPoints()));
        holder.rankingPosition.setText(String.valueOf(photographer.getPosition()));

    }

    @Override
    public int getItemCount() {
        return photographers.size();
    }

    public class PhotographersViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.circular_avatar_photographers)
    CircularImageView avatar;
    @BindView(R.id.user_name_photographers)
    TextView name;
    @BindView(R.id.ranking_points_photographers)
    TextView rankingPoints;
    @BindView(R.id.ranking_position_photographers)
    TextView rankingPosition;

        public PhotographersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(ArrayList<Photographer> photographers){
        this.photographers = photographers;
    }

}
