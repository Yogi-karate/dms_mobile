package com.dealermanagmentsystem.ui.enquiry.enquirysubenquirylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
    SearchView mSearchView;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) mSearch.getActionView();

        EditText searchEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //searchEditText.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey));
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.textPrimary));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.textSecondary));

        mSearchView.setQueryHint("Search by product and name");


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                enquiryAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
            mSearchView.setIconified(true);
            mSearchView.clearFocus();
        }
    }
}
