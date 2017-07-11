package com.example.arek.visium;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by arek on 7/9/2017.
 */

public class MyGridLayoutManager extends GridLayoutManager {

    private static final float MILISEC_PER_INCH =300f;
    private int mFirstVisiblePosition;

    private Context mContext;

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        mContext = context;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);

        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mContext){

            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {

                return MyGridLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {

                return MILISEC_PER_INCH/displayMetrics.densityDpi;
            }
        };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }
}
