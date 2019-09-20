package com.dealermanagmentsystem.ui.insurance.booking;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.InsuranceBookAdapter;
import com.dealermanagmentsystem.data.model.bookinginsurance.InsuranceBookingResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsuranceBookingActivity extends BaseActivity implements IInsuranceBookingView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    SearchView mSearchView;

    InsuranceBookingPresenter presenter;
    InsuranceBookAdapter insuranceBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_leads_booking);

        activity = InsuranceBookingActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Insurance Booking");
        showBackButton();

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        presenter = new InsuranceBookingPresenter(this);
        presenter.getInsuranceBookings(activity);

    }

    @Override
    public void onSuccessInsuranceBooking(InsuranceBookingResponse insuranceBookingResponse) {
        if (insuranceBookingResponse.getResult().getRecords() != null) {
            insuranceBookAdapter = new InsuranceBookAdapter(this, insuranceBookingResponse.getResult().getRecords());
            recyclerView.setAdapter(insuranceBookAdapter);
        }
        showTile("Booking - "+String.valueOf(insuranceBookingResponse.getResult().getLength()));
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) mSearch.getActionView();

        EditText searchEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.textPrimary));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.textSecondary));

        mSearchView.setQueryHint("Search by customer and mobile");


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                insuranceBookAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
