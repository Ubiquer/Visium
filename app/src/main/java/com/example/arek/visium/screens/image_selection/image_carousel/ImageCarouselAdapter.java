package com.example.arek.visium.screens.image_selection.image_carousel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.arek.visium.R;
import com.example.arek.visium.VisiumApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 7/27/2017.
 */

public class ImageCarouselAdapter extends RecyclerView.Adapter<ImageCarouselAdapter.CarouselViewHolder> {

    private ArrayList<String> picsFromExternalStorage;
//    private final RequestManager requestManager;

    @Override
    public CarouselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarouselViewHolder holder, int position) {

        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .override(250, 250);

        final String picture = picsFromExternalStorage.get(position % picsFromExternalStorage.size());
        Glide.with(VisiumApplication.getContext()).asBitmap().apply(myOptions).load(picture).into(holder.carouselImage);
    }

    @Override
    public int getItemCount() {
        return picsFromExternalStorage == null ? 0 : picsFromExternalStorage.size()*2;
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.carousel_image)
        ImageView carouselImage;

        public CarouselViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setData(ArrayList<String> picsFromExternalStorage){
        this.picsFromExternalStorage = picsFromExternalStorage;
        notifyDataSetChanged();
    }

}
