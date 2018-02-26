package com.example.arek.visium.screens.menu.account_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arek.visium.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arek on 2/25/2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ArrayList<String> preferenceList;

    public CategoriesAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String preference = preferenceList.get(position);
        holder.preferenceTextView.setText(preference);
    }

    @Override
    public int getItemCount() {
        return preferenceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_preference_name)
        TextView preferenceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setData(ArrayList<String> preferenceList) {
        this.preferenceList = preferenceList;
    }
}

//    public CategoriesAdapter(Context mContext, ArrayList<String> categories) {
//        this.mContext = mContext;
//        this.categories = categories;
//    }
//
//    @Override
//    public int getCount() {
//        return categories.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return categories.get(position);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        if (view == null) {
//            final LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
//            view = layoutInflater.inflate(R.layout.category_grid_item, null);
//        }
//
//        TextView categoryTextView = view.findViewById(R.id.grid_preference_name);
//        categoryTextView.setText(categories.get(i));
//
//        return categoryTextView;

