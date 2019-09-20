package com.dealermanagmentsystem.ui.userenquiry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.TasksAdapter;
import com.dealermanagmentsystem.adapter.UserDetailAdapter;
import com.dealermanagmentsystem.data.model.userdetail.UserDetailResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_NAME;

public class UserMonthDetailsActivity extends BaseActivity implements IUserDetailView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    UserDetailAdapter userDetailAdapter;
    UserDetailPresenter presenter;
    String strId;
    String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_month_details);

        activity = UserMonthDetailsActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        presenter = new UserDetailPresenter(this);

        final Intent intent = getIntent();
        if (intent != null) {
            strId = intent.getStringExtra(EXTRA_USER_ID);
            strName = intent.getStringExtra(EXTRA_USER_NAME);
        }

        showTile(strName);
        showBackButton();

        presenter.getUserDetail(activity, strId);
    }

    @Override
    public void onSuccess(List<UserDetailResponse> responses) {
        if (responses != null) {
            Collections.reverse(responses);
            userDetailAdapter = new UserDetailAdapter(this, responses);
            recyclerView.setAdapter(userDetailAdapter);
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}
