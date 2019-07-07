package com.exam.myapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wuxiaolong.androidutils.library.LogUtil;

public class DBManager {
    private SQLiteDatabase database;

    public DBManager(Context context) {
        DBopenHelper helper = new DBopenHelper(context);
        database = helper.getWritableDatabase();
    }

    public void insertBook(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        database.insert(DBopenHelper.BOOK_TABLE_NAME, null, values);
    }

    public void queryBook() {
        Cursor cursor = database.query(DBopenHelper.BOOK_TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            LogUtil.w("name:" + cursor.getString(1));
        }
    }

    public void insertPerson(String name, int age, String sex) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("sex", sex);
        database.insert(DBopenHelper.PERSON_TABLE_NAME, null, values);
    }

    public void queryPerson() {
        Cursor cursor = database.query(DBopenHelper.PERSON_TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            LogUtil.w("name:" + cursor.getString(1) + " age:" + cursor.getInt(2) + " sex:" + cursor.getString(3));
        }
    }

}
