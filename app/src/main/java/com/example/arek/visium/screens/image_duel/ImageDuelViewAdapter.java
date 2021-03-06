package com.example.arek.visium.screens.image_duel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arek.visium.R;
import com.example.arek.visium.dao.DuelImage;
import com.example.arek.visium.rest.ApiKeys;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 9/11/2017.
 */

public class ImageDuelViewAdapter extends RecyclerView.Adapter<ImageDuelViewAdapter.ImageDuelViewHolder> {

    private ArrayList<DuelImage> duelImageList;
    private int mDisplayHeight;
    private int mDisplayWidth;

    public ImageDuelViewAdapter() {}

    @Override
    public ImageDuelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.duel_row, parent, false);
        return new ImageDuelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageDuelViewHolder holder, int position) {

        final DuelImage model = duelImageList.get(position);
        Picasso.with(holder.duelImage.getContext())
                    .load(ApiKeys.BASE_URL + model.getImagePath())
                    .resize(mDisplayWidth, mDisplayHeight/2)
                    .into(holder.duelImage);
    }

    @Override
    public int getItemCount() {
        return duelImageList.size();
    }

    public class ImageDuelViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.duel_image)
        ImageView duelImage;

        public ImageDuelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(ArrayList<DuelImage> duelImageList, int displayHeight, int displayWidth) {
        this.duelImageList = duelImageList;
        this.mDisplayHeight = displayHeight;
        this.mDisplayWidth = displayWidth;
        notifyDataSetChanged();
    }

    public ArrayList<DuelImage> getImages() {
        return duelImageList;
    }
}
