package com.example.arek.visium.screens.image_selection.image_carousel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.arek.visium.VisiumApplication;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by arek on 11/13/2017.
 */

public class ImageCarouselRepository {

    private ArrayList<String> arrPath;
    private final ContentResolver resolver;

    @Inject
    public ImageCarouselRepository(ContentResolver resolver) {
        this.resolver = resolver;
    }

    public ArrayList<String> getImagesFromExternalStorage() {

        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        int count = cursor.getCount();
        arrPath = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath.add(i, cursor.getString(dataColumnIndex));
        }
        cursor.close();
        return arrPath;
    }

}
