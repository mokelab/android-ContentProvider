package com.mokelab.sample.contentprovider.provider.todo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.CursorAdapter;

/**
 * DAO for Todo
 */
class TodoDAO {
    private static final String TABLE_NAME = "todo";

    private static final String SQL_CREATE = "create table " + TABLE_NAME + "(" +
            TodoColumns._ID + " integer primary key autoincrement," +
            TodoColumns.USER_ID + " text," +
            TodoColumns.TODO + " text," +
            TodoColumns.MODIFIED_TIME + " integer," +
            TodoColumns.SERVER_ID + " text," +
            TodoColumns.SERVER_TIME + " integer)";

    private SQLiteDatabase mDB;

    TodoDAO(SQLiteOpenHelper helper) {
        mDB = helper.getWritableDatabase();
    }

    static void createTable(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    Cursor query(String userId, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] args;
        // add user_id=? to selection
        if (TextUtils.isEmpty(selection)) {
            selection = TodoColumns.USER_ID + "=?";
            args = new String[] { userId };
        } else {
            selection += " AND " + TodoColumns.USER_ID + "=?";
            args = new String[selectionArgs.length + 1];
            System.arraycopy(selectionArgs, 0, args, 0, selectionArgs.length);
            args[args.length - 1] = userId;
        }

        return mDB.query(TABLE_NAME, projection, selection, args, null, null, sortOrder);
    }

    long insert(String userId, String serverId, long modifiedTime, long serverTime, String todo) {
        ContentValues values = new ContentValues();
        values.put(TodoColumns.USER_ID, userId);
        values.put(TodoColumns.SERVER_ID, serverId);
        values.put(TodoColumns.MODIFIED_TIME, modifiedTime);
        values.put(TodoColumns.SERVER_TIME, serverTime);
        values.put(TodoColumns.TODO, todo);

        // SQLiteDatabase#insert() returns _id
        return mDB.insert(TABLE_NAME, null, values);
    }
}
