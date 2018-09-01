package com.andrii_gerashchenko.weatherandrii.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weatherDB";
    public static final String TABLE_WEATHER = "weather";

    public static final String KEY_ID = "_id";
    public static final String KEY_CITY = "city";
    public static final String KEY_TEMP = "tempr";
    public static final String KEY_DATE = "date";
    public static final String KEY_ICON = "icon";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table " + TABLE_WEATHER + "(" + KEY_ID + " integer primary key," +
        KEY_CITY + " text," + KEY_TEMP + " text," + KEY_DATE + " text," + KEY_ICON + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("drop table if exists " + TABLE_WEATHER);

        onCreate(database);
    }
}
