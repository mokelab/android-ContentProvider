package com.mokelab.sample.contentprovider.provider.todo;

import android.provider.BaseColumns;

/**
 * Todo Columns
 */
public interface TodoColumns extends BaseColumns {
    String USER_ID = "user_id";
    String TODO = "todo";
    String MODIFIED_TIME = "modified_time";
    String SERVER_ID = "server_id";
    String SERVER_TIME = "server_time";
}
