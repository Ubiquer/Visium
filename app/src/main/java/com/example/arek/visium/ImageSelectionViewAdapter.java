package com.example.arek.visium;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 7/23/2017.
 */

public class ImageSelectionViewAdapter extends RecyclerView.Adapter<ImageSelectionViewAdapter.ImageCarouselViewHolder>{


    @Override
    public ImageCarouselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_item, parent, false);
        return new ImageCarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageCarouselViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ImageCarouselViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.id_image_carousel_item)
        ImageView imageCarouselItem;

        public ImageCarouselViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
