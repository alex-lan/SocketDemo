package com.exam.myapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exam.myapp.utils.DBopenHelper;
import com.wuxiaolong.androidutils.library.LogUtil;

public class BookContentProvider extends ContentProvider {
    public static final String AUTHROTY = "com.exam.myapp.privider";
    public static final int BOOK_URI_CODE = 1;
    public static final int PERSON_URI_CODE = 2;
    private static final UriMatcher mUriMathcher = new UriMatcher(UriMatcher.NO_MATCH);
    private Context context;
    private SQLiteDatabase database;

    static {
        mUriMathcher.addURI(AUTHROTY, "book", BOOK_URI_CODE);
        mUriMathcher.addURI(AUTHROTY, "person", PERSON_URI_CODE);
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMathcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DBopenHelper.BOOK_TABLE_NAME;
                break;
            case PERSON_URI_CODE:
                tableName = DBopenHelper.PERSON_TABLE_NAME;
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        LogUtil.e(BookContentProvider.class.getSimpleName()
                + " onCreate() current thread: " + Thread.currentThread().getName());
        context = getContext();
        database = new DBopenHelper(context).getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        String tableName = getTableName(uri);
        if (tableName != null) {
            cursor = database.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if (tableName != null) {
            database.insert(tableName, null, values);
            context.getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int count = 0;
        if (tableName != null) {
            count = database.delete(tableName, selection, selectionArgs);
            if (count > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int row = 0;
        if (tableName != null) {
            row = database.update(tableName, values, selection, selectionArgs);
            if (row > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
        }
        return row;
    }
}
