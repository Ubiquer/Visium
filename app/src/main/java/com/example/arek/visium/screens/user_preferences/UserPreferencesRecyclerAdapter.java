package com.example.arek.visium.screens.user_preferences;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arek.visium.R;
import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.dao.UserPreferencesWithImage;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 7/8/2017.
 */

public class UserPreferencesRecyclerAdapter extends RecyclerView.Adapter<UserPreferencesRecyclerAdapter.MyViewHolder>{

    private ArrayList<UserPreferencesWithImage> categories;
    private ArrayList<Integer> selectedPreferencesIds;

    public UserPreferencesRecyclerAdapter(){
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final UserPreferencesWithImage preferenceItem = categories.get(position);
        holder.preferenceName.setText(preferenceItem.getCategoryName());
        Picasso.with(holder.image.getContext())
                .load(ApiKeys.BASE_URL + preferenceItem.getImagePath())
                .resize(300, 300)
                .centerInside()
                .into(holder.image);

        holder.image.setSelected(false);
        holder.image.setOnClickListener(v -> {
            if (preferenceItem.isSelected()){
                preferenceItem.setSelected(false);
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                Log.d("Item disabled: ", String.valueOf(preferenceItem));
                getSelectedItems();

            }else {
                preferenceItem.setSelected(true);
                holder.itemView.setBackgroundColor(Color.CYAN);
                Log.d("Item enabled: ", String.valueOf(preferenceItem));
                getSelectedItems();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_img)
        ImageView image;
        @BindView(R.id.preference_name)
        TextView preferenceName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public List getSelectedItems() {
        List itemModelList = new ArrayList<String>();
        selectedPreferencesIds = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            String preferenceName;
            int preferenceId;
            UserPreferencesWithImage item = categories.get(i);
            preferenceName = item.getCategoryName();
            preferenceId = item.getCategoryId();
            if (item.isSelected()) {
                itemModelList.add(preferenceName);
                selectedPreferencesIds.add(preferenceId);
            }
        }
        return itemModelList;
    }

    public ArrayList<Integer> getPreferences(){
        return selectedPreferencesIds;
    }

    public void setData(ArrayList<UserPreferencesWithImage> mPreferenceItems){
        this.categories = mPreferenceItems;
    }

}

