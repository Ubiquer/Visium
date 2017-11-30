package com.example.arek.visium.screens.image_selection;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by arek on 7/27/2017.
 */

public class ImageCarouselPresenter {

    Context context;

    private ImageCarouselView mImageCarouselView;

    public ImageCarouselPresenter(Context context, ImageCarouselView mImageCarouselView) {
        this.context = context;
        this.mImageCarouselView = mImageCarouselView;
    }

    public ImageCarouselPresenter() {

    }

    public void attemptToPassData(){

        imageMediaQuery();
    }

    private void imageMediaQuery() {
        String[] projection = new String[]{
                MediaStore.MediaColumns.DATA,
                //MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String BUCKET_GROUP_BY =
                "1) GROUP BY 1,(1";
        String BUCKET_ORDER_BY = MediaStore.Images.Media.DATE_TAKEN + " DESC";

        Cursor cur = context.getContentResolver().query(images,
                projection, // Which columns to return
                BUCKET_GROUP_BY,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                BUCKET_ORDER_BY        // Ordering
        );

        if(cur!=null){
            Log.i("ListingImages", " query count=" + cur.getCount());
        }

        if (cur!=null&&cur.moveToFirst()) {
            String bucket;
            String path;
            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int pathColumn = cur.getColumnIndex(
                    MediaStore.MediaColumns.DATA);

            do {
                bucket = cur.getString(bucketColumn);
                path = cur.getString(pathColumn);

            } while (cur.moveToNext());
            cur.close();
        }
    }

}
