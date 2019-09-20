package com.dealermanagmentsystem.ui.service.booking;

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
import com.dealermanagmentsystem.adapter.ServiceBookAdapter;
import com.dealermanagmentsystem.data.model.bookingservice.ServiceBookingResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceBookingActivity extends BaseActivity implements IServiceBookingView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    ServiceBookingPresenter presenter;
    ServiceBookAdapter serviceBookAdapter;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking);

        activity = ServiceBookingActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Service Booking");
        showBackButton();

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        presenter = new ServiceBookingPresenter(this);
        presenter.getServiceBookings(activity);

    }

    @Override
    public void onSuccessServiceBooking(ServiceBookingResponse serviceBookingResponse) {
        if (serviceBookingResponse.getResult().getRecords() != null) {
            serviceBookAdapter = new ServiceBookAdapter(this, serviceBookingResponse.getResult().getRecords());
            recyclerView.setAdapter(serviceBookAdapter);
        }
        showTile("Booking - "+String.valueOf(serviceBookingResponse.getResult().getLength()));
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
                serviceBookAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
