package com.example.arek.visium.screens.image_duel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.arek.visium.R;

/**
 * Created by arek on 9/18/2017.
 */

public class ItemSwipeHelper extends ItemTouchHelper.SimpleCallback{


    private ImageDuelViewAdapter mAdapter;
    private Context mContext;
    private Paint paint = new Paint();
    private final String rightColorCode = "#388E3C";
    private final String leftColorCode = "#D32F2F";
    private ImageChooseListener listener;

    public interface ImageChooseListener {
        void onImageChosen(int position, boolean winner);
    }

    public ItemSwipeHelper(int dragDirs, int swipeDirs, ImageDuelViewAdapter adapter,Context context, ImageChooseListener listener) {

        super(dragDirs, swipeDirs);
        this.mAdapter = adapter;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

       try {
           if (direction == ItemTouchHelper.LEFT) {
               listener.onImageChosen(viewHolder.getAdapterPosition(), false);
           }else{
               listener.onImageChosen(viewHolder.getAdapterPosition(), true);
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
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_lock);
                    RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                    c.drawBitmap(mIcon, null, icon_dest, paint);
                } else {
                    paint.setColor(Color.parseColor(leftColorCode));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, paint);
                    mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_lock);
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
