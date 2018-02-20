package com.example.arek.visium.screens.image_selection.image_carousel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arek.visium.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 7/27/2017.
 */

public class ImageCarouselAdapter extends RecyclerView.Adapter<ImageCarouselAdapter.CarouselViewHolder> {

    private ArrayList<String> picsFromExternalStorage;

    @Override
    public CarouselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarouselViewHolder holder, int position) {

        final String picture = picsFromExternalStorage.get(position % picsFromExternalStorage.size());
        Bitmap bitmap = BitmapFactory.decodeFile(picture);
        holder.carouselImage.setImageBitmap(bitmap);
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
