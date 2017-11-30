package com.example.arek.visium.screens.image_selection.image_carousel;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arek.visium.model.Category;

import java.util.ArrayList;

/**
 * Created by arek on 11/23/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<Category> {

    private ArrayList<Category> categories;
    private Context context;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Category> categories) {
        super(context, resource, categories);
        this.categories = categories;
        this.context = context;
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

        TextView label = new TextView(context);
        label.setTextColor(Color.CYAN);
        label.setText((CharSequence) categories.get(position));
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView label = new TextView(context);
        label.setTextColor(Color.CYAN);
        label.setText((CharSequence) categories.get(position));

        return label;
    }
}
