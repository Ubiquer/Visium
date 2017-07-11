package com.example.arek.visium;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arek.visium.model.UserPreferencesImageModel;
import com.squareup.picasso.Picasso;
import rx.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 7/8/2017.
 */

public class UserPreferencesViewAdapter extends RecyclerView.Adapter<UserPreferencesViewAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener{

    private Context context;
    private ArrayList<UserPreferencesImageModel> data = new ArrayList<>();

    private SparseBooleanArray selectedItems;


    public UserPreferencesViewAdapter(Context context, ArrayList<UserPreferencesImageModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Picasso.with(holder.image.getContext())
                .load(data.get(position).getImageId())
                .resize(320, 320)
                .centerInside()
                .into(holder.image);

//        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_img);

        }

        @Override
        public void onClick(View v) {

        }
    }

    public void toggleSelection(int pos){

        if(selectedItems.get(pos, false)){
            selectedItems.delete(pos);
        }else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections(){

        selectedItems.clear();
        notifyDataSetChanged();

    }

    public int getSelectedItemCount(){
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems(){

        List<Integer> items = new ArrayList<>(selectedItems.size());
        for(int i=0; i<selectedItems.size(); i++){
            items.add(selectedItems.keyAt(i));
        }

        return items;

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }




    @Override
    public int getItemCount() {
        return data.size();
    }

}

