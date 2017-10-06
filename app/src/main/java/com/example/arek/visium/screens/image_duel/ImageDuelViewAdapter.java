package com.example.arek.visium.screens.image_duel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arek.visium.R;
import com.example.arek.visium.model.ImageDuelModel;
import com.example.arek.visium.rest.IntentKeys;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 9/11/2017.
 */

public class ImageDuelViewAdapter extends RecyclerView.Adapter<ImageDuelViewAdapter.ImageDuelViewHolder> {

    private ArrayList<ImageDuelModel> duelImageList;
    private int mDisplayHeight;
    private int mDisplayWidth;

    public ImageDuelViewAdapter(ArrayList<ImageDuelModel> duelImageList, int displayHeight, int displayWidth) {
        this.duelImageList = duelImageList;
        this.mDisplayHeight = displayHeight;
        this.mDisplayWidth = displayWidth;
    }

    @Override
    public ImageDuelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.duel_row, parent, false);
        return new ImageDuelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageDuelViewHolder holder, int position) {

        final ImageDuelModel model = duelImageList.get(position);
        Picasso.with(holder.duelImage.getContext())
                    .load(IntentKeys.BASE_URL + model.getImagePath())
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

}
