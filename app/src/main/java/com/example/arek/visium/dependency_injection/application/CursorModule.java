package com.example.arek.visium.dependency_injection.application;

/**
 * Created by arek on 2/17/2018.
 */

import android.content.Context;
import android.database.Cursor;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApplicationModule.class)
public class CursorModule {

    private final Cursor cursor;

    public CursorModule(Cursor cursor) {
        this.cursor = cursor;
    }

    @Provides
    @VisiumApplicationScope
    public Cursor cursor(@ApplicationContext Context context){
        return cursor;
    }

}
