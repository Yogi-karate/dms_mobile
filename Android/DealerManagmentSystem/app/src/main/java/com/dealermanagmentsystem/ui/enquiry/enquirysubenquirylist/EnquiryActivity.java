package com.dealermanagmentsystem.ui.enquiry.enquirysubenquirylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.EnquiryAdapter;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.CREATE_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;

public class EnquiryActivity extends BaseActivity implements IEnquiryView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.fab_create_enquiry)
    FloatingActionButton fabCreateEnquiry;
    Activity activity;
    EnquiryPresenter presenter;
    EnquiryAdapter enquiryAdapter;
    String stringExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        activity = EnquiryActivity.this;
        ButterKnife.bind(this);

        presenter = new EnquiryPresenter(this);

        setStatusBarColor(getResources().getColor(R.color.bg));

        final Intent intent = getIntent();
        if (intent != null) {
            stringExtra = intent.getStringExtra(EXTRA_ENQUIRY);
            if (stringExtra.equalsIgnoreCase(ENQUIRY)) {
                showTile("Enquiry");
                showBackButton();
                presenter.getEnquiry(activity);
            } else {
                showTile("Sub Enquiry");
                showBackButton();
                fabCreateEnquiry.setVisibility(View.GONE);
                presenter.getSubEnquiry(activity);
            }
        }

    }

    @Override
    public void onSuccessEnquiry(EnquiryResponse enquiryResponse) {
        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        if (enquiryResponse.getRecords() != null) {
            enquiryAdapter = new EnquiryAdapter(this, enquiryResponse.getRecords(), stringExtra);
            recyclerView.setAdapter(enquiryAdapter);
        }

    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @OnClick(R.id.fab_create_enquiry) //ButterKnife uses.
    public void openCreateEnquiry() {
        Intent intent = new Intent(this, CreateEnquiryActivity.class);
        intent.putExtra(EXTRA_ENQUIRY, CREATE_ENQUIRY);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            presenter.getEnquiry(activity);
        }
    }
}
