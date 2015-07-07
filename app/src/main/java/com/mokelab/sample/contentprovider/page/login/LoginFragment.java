package com.mokelab.sample.contentprovider.page.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mokelab.sample.contentprovider.R;
import com.mokelab.sample.contentprovider.page.list.TodoListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment for Login page
 */
public class LoginFragment extends Fragment {
    @Bind(android.R.id.edit)
    EditText mUsernameEdit;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @OnClick(android.R.id.button1)
    void loginClicked() {
        String username = mUsernameEdit.getText().toString();
        if (TextUtils.isEmpty(username)) { return; }

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, TodoListFragment.newInstance(username));

        transaction.commit();

    }
}
