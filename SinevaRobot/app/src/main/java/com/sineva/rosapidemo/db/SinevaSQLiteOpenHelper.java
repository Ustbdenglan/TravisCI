package com.sineva.rosapidemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eligah on 2017/9/22.
 */

public class SinevaSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public SinevaSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                  int version) {
        super(context, name, factory, version);
    }

    public SinevaSQLiteOpenHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public SinevaSQLiteOpenHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS button(_id INTEGER PRIMARY KEY, time LONG)");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
