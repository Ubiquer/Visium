package com.example.arek.visium.screens.image_selection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arek.visium.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 7/27/2017.
 */

class ImageCarouselAdapter extends RecyclerView.Adapter<ImageCarouselAdapter.CarouselViewHolder> {

    @Override
    public CarouselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarouselViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.carousel_image)
        ImageView carouselImage;

        public CarouselViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
