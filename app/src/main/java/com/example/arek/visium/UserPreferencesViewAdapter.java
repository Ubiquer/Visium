package com.example.arek.visium;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.arek.visium.model.UserPreferencesImageModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 7/8/2017.
 */

public class UserPreferencesViewAdapter extends RecyclerView.Adapter<UserPreferencesViewAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<UserPreferencesImageModel> itemModel;
    private static final int TYPE_INACTIVE = 0;
    private static final int TYPE_ACTIVE = 1;


    public UserPreferencesViewAdapter(Context context, ArrayList<UserPreferencesImageModel> data) {
        this.context = context;
        this.itemModel = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final UserPreferencesImageModel model = itemModel.get(position);

        Picasso.with(holder.image.getContext())
                .load(itemModel.get(position).getImageId())
                .resize(320, 320)
                .centerInside()
                .into(holder.image);

//        holder.itemView.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                model.setSelected(!model.isSelected());
////                notifyDataSetChanged();
//                holder.itemView.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
//            }
//        });
////        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        @BindView(R.id.item_img)
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
        }

        @Override
        public void onClick(View v) {


        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return itemModel.size();
    }

    public List getSelectedItem(){

        List itemModelList = new ArrayList<>();
        for (int i =0; i< getItemCount(); i++){
            UserPreferencesImageModel item = itemModel.get(i);

            if (item.isSelected()){
                itemModelList.add(itemModel);
            }
        }

        return itemModelList;

    }

}

