package com.mokelab.sample.contentprovider.page.list;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import com.mokelab.sample.contentprovider.R;
import com.mokelab.sample.contentprovider.provider.todo.TodoColumns;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment for Todo List page
 */
public class TodoListFragment extends ListFragment {
    private static final String ARGS_USERNAME = "username";
    private static final int ID_LOADER = 1;

    // argument
    private String mUsername;

    @Bind(android.R.id.edit)
    EditText mEdit;

    public static TodoListFragment newInstance(String username) {
        TodoListFragment fragment = new TodoListFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_USERNAME, username);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUsername = args.getString(ARGS_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, root);

        // init adapter
        CursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_todo, null,
                new String[]{TodoColumns.TODO}, new int[]{android.R.id.text1}, 0);
        setListAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadTodo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private void loadTodo() {
        LoaderManager manager = getLoaderManager();
        manager.initLoader(ID_LOADER, null, mCallback);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), Uri.parse("content://com.mokelab.todo.provider/users/" + mUsername + "/todos"),
                    null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            CursorAdapter adapter = (CursorAdapter) getListAdapter();
            adapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            CursorAdapter adapter = (CursorAdapter) getListAdapter();
            adapter.swapCursor(null);
        }
    };

    @OnClick(android.R.id.button1)
    void addClicked() {
        String todo = mEdit.getText().toString();
        if (TextUtils.isEmpty(todo)) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(TodoColumns.TODO, todo);
        getActivity().getContentResolver().insert(
                Uri.parse("content://com.mokelab.todo.provider/users/" + mUsername + "/todos"),
                values);
        mEdit.setText("");
    }
}
