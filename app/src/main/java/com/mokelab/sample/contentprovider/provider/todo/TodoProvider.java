package com.mokelab.sample.contentprovider.provider.todo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Content Provider for Todo
 */
public class TodoProvider extends ContentProvider {

    private static final String AUTHORITY = "com.mokelab.todo.provider";

    // region URI type
    private static final int URITYPE_TODO_LIST = 1;
    private static final int URITYPE_TODO = 2;
    // endregion

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "users/*/todos", URITYPE_TODO_LIST);
        sUriMatcher.addURI(AUTHORITY, "users/*/todos/#", URITYPE_TODO);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }
}
