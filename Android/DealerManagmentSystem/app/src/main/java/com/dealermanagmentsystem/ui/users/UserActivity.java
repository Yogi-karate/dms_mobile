package com.dealermanagmentsystem.ui.users;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.UserLoadAdapter;
import com.dealermanagmentsystem.data.model.loadusers.LoadUserResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserActivity extends BaseActivity implements IUserView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;

    Activity activity;
    UserPresenter presenter;
    UserLoadAdapter userLoadAdapter;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        activity = UserActivity.this;
        ButterKnife.bind(this);

        presenter = new UserPresenter(this);

        showTile("User Details");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        presenter.getUsers(activity, "launch");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_load_user, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearch.getActionView();

        EditText searchEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.textPrimary));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.textSecondary));

        mSearchView.setQueryHint("Search by name or number");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userLoadAdapter.filter(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.getUsers(activity, "refresh");
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onSuccessUserList(LoadUserResponse loadUserResponse) {
        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        if (loadUserResponse.getNewUserCount() > 0) {
            DMSToast.showLong(activity, String.valueOf(loadUserResponse.getNewUserCount()) + " New user has been added");
        }

        if (loadUserResponse.getUsers().size() > 0) {
            userLoadAdapter = new UserLoadAdapter(activity, loadUserResponse.getUsers());
            recyclerView.setAdapter(userLoadAdapter);
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }



}
