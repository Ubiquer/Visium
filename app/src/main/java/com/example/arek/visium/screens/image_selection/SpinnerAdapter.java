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
//    private final LayoutInflater inflater;
    private int resource;
    private LinearLayout llWrapper;
    private TextView categoryText, tvDropDownItem;
    private ImageView underLine;
    private int border;
//    @BindView(R.id.tv_spinner_item)
//    TextView categoryText;
//    @BindView(R.id.tv_underline)
//    ImageView underline;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Category> categories) {
        super(context, resource, categories);
        this.categories = categories;
        this.context = context;
//        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
//        border = (int) context.getResources().getDimension(R.dimen.);
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
//        return createItemViewBasic(position, convertView, parent);
//        TextView label = new TextView(context);
//        label.setTextColor(Color.CYAN);
//        label.setGravity(Gravity.CENTER);
//        label.setTextSize(16);
//        label.setPadding(16,16,16,16);
//        Category category = categories.get(position);
////        label.setText("Category");
//        label.setText(category.getCategoryName());
//        return label;
        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        return createItemView(position, convertView, parent);

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
//            params.setMargins();
        }

//
//        if (position == categories.size() - 1){
//            underLine.setVisibility(View.GONE);
////            int padding = (int)context.getResources().getDimension(R.dimen.activity_vertical_margin_small);
////            int paddingTop = (int)context.getResources().getDimension(R.dimen.rounding_radius_dp);
////            tvItem.setPadding(padding, paddingTop, 0, padding);
//            params.setMargins(border, 0, border, border); //end part
//            llWrapper.setLayoutParams(params);
//        } else if(position == 0){
//            params.setMargins(border, border, border, 0); //start part
//            llWrapper.setLayoutParams(params);
//        } else {
//            params.setMargins(border, 0, border, 0); //middle part
//            llWrapper.setLayoutParams(params);
//        }
//        TextView label = new TextView(context);
//        label.setTextColor(Color.CYAN);
//        label.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary, null));
//        label.setTextSize(16);
//        label.setGravity(Gravity.CENTER);
//        label.setPadding(16,16,16,16);
//        Category category = categories.get(position);
//        category.getCategoryName();
//        label.setText(category.getCategoryName());
//
//        return label;
//    }

        return row;

    }
}