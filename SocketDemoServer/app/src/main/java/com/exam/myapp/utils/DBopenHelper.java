package com.exam.myapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBopenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "content_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String PERSON_TABLE_NAME = "person";
    private static final int DB_VERSION = 1;

    public DBopenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + BOOK_TABLE_NAME + " (_id integer PRIMARY KEY, name text)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PERSON_TABLE_NAME + " (_id integer PRIMARY KEY, name text, age int, sex text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
