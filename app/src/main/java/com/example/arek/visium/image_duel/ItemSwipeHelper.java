package com.example.arek.visium.image_duel;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.arek.visium.R;
import com.example.arek.visium.adapters.ImageDuelViewAdapter;
import com.example.arek.visium.adapters.UserPreferencesViewAdapter;
import com.example.arek.visium.model.ImageDuelModel;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

/**
 * Created by arek on 9/18/2017.
 */

public class ItemSwipeHelper extends ItemTouchHelper.SimpleCallback{


    private ImageDuelViewAdapter mAdapter;
//    private ItemSwipeHelperAdapter swipeHelperAdapter;
    private OnSwipeRecyclerViewListener mListener;
    private Context mContext;
    private Paint paint = new Paint();
    private String rightColorCode;
    private String leftColorCode;
    private ApiInterface mApiInterface;


    public ItemSwipeHelper(int dragDirs, int swipeDirs, ImageDuelViewAdapter adapter,Context context, String rightColorCode, String leftColorCode) {

        super(dragDirs, swipeDirs);
        this.mAdapter = adapter;
        this.mContext = context;
        this.rightColorCode = rightColorCode;
        this.leftColorCode = leftColorCode;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

       try {
           if (direction == ItemTouchHelper.LEFT) {
                int position = viewHolder.getAdapterPosition();

           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        Bitmap mIcon;

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;
            try {
                if (dX > 0) {
                    paint.setColor(Color.parseColor(rightColorCode));
                    RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                    c.drawRect(background, paint);
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_provider);
                    RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                    c.drawBitmap(mIcon, null, icon_dest, paint);
                } else {

                    paint.setColor(Color.parseColor(leftColorCode));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, paint);
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_provider);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(mIcon, null, icon_dest, paint);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
