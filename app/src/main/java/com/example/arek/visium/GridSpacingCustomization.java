package com.example.arek.visium;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

/**
 * Created by arek on 7/9/2017.
 */

//TODO: Write class for GridLayout spacing customization

public class GridSpacingCustomization extends RecyclerView.ItemDecoration {

    private static final int VERTICAL = OrientationHelper.VERTICAL;

    private int orientation = -1;
    private int spanCount = -1;
    private int spacing;
    private int halfSpacing;

}
