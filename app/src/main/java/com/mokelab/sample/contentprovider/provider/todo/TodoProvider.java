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

    private TodoDB mTodoDB;
    private TodoDAO mTodoDAO;

    @Override
    public boolean onCreate() {
        mTodoDB = new TodoDB(getContext());
        mTodoDAO = new TodoDAO(mTodoDB);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
        case URITYPE_TODO_LIST:
            return queryTodoList(uri, projection, selection, selectionArgs, sortOrder);
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
        case URITYPE_TODO_LIST:
            return insertLocal(uri, contentValues);
        }
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

    private Uri insertLocal(Uri uri, ContentValues contentValues) {
        // Uri = content://com.mokelab.todo.provider/users/{userId}/todos
        // pathSegments[0] = users
        // pathSegments[1] = {userId}
        // pathSegments[2] = todos
        String userId = uri.getPathSegments().get(1);
        String todo = contentValues.getAsString(TodoColumns.TODO);

        long id = mTodoDAO.insert(userId, "", System.currentTimeMillis(), 0, todo);
        if (id == -1) { return null; }

        // notify update
        Uri notifyUri = Uri.parse("content://" + AUTHORITY + "/users/" + userId + "/todos");
        getContext().getContentResolver().notifyChange(notifyUri, null);

        return Uri.parse("content://" + AUTHORITY + "/users/" + userId + "/todos/" + id);
    }

    private Cursor queryTodoList(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uri = content://com.mokelab.todo.provider/users/{userId}/todos
        // pathSegments[0] = users
        // pathSegments[1] = {userId}
        // pathSegments[2] = todos
        String userId = uri.getPathSegments().get(1);
        Cursor cursor = mTodoDAO.query(userId, projection, selection, selectionArgs, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
}
