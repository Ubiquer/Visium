package com.example.arek.visium.screens.image_selection;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arek.visium.R;
import com.example.arek.visium.model.Category;

import java.util.ArrayList;

/**
 * Created by arek on 11/23/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<Category> {

    private ArrayList<Category> categories;
    private Context context;
    private int resource;
    private LinearLayout llWrapper;
    private TextView categoryText, tvDropDownItem;
    private ImageView underLine;
    private int border;


    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Category> categories) {
        super(context, resource, categories);
        this.categories = categories;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;

        row = inflater.inflate(R.layout.item_spinner_row, parent, false);
        categoryText = (TextView) row.findViewById(R.id.tv_spinner_item);
        Category category = categories.get(position);
        categoryText.setGravity(Gravity.CENTER);
        categoryText.setText(category.getCategoryName());
        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;

        row = View.inflate(context, R.layout.item_spinner_dropdown, null);
        llWrapper = (LinearLayout) row.findViewById(R.id.ll_wrapper);
        tvDropDownItem = (TextView) row.findViewById(R.id.tv_dropdown_item);
        Category category = categories.get(position);
        tvDropDownItem.setText(category.getCategoryName());
        tvDropDownItem.setGravity(Gravity.CENTER);
        underLine = (ImageView) row.findViewById(R.id.tv_underline);
        llWrapper.setPadding(16,16,16,16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        if (position == categories.size() -1){
            underLine.setVisibility(View.GONE);
        }
        return row;
    }
}