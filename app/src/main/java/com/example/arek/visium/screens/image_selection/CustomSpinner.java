package com.example.arek.visium.screens.image_selection;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by arek on 11/25/2017.
 */

public class CustomSpinner extends android.support.v7.widget.AppCompatSpinner {

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, int mode) {
        super(context, mode);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }
}
