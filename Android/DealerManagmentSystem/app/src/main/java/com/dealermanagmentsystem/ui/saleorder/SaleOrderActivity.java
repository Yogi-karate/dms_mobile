package com.dealermanagmentsystem.ui.saleorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.dealermanagmentsystem.adapter.SaleOrderAdapter;
import com.dealermanagmentsystem.data.model.saleorder.SaleOrderResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE_ID;

public class SaleOrderActivity extends BaseActivity implements ISaleOrderView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    SaleOrderPresenter presenter;
    SaleOrderAdapter saleOrderAdapter;
    String strSaleType;
    String strSaleTypeID;
    String leadId;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order);

        activity = SaleOrderActivity.this;
        ButterKnife.bind(this);

        presenter = new SaleOrderPresenter(this);

        final Intent intent = getIntent();
        if (intent != null) {
            strSaleType = intent.getStringExtra(EXTRA_SALE_TYPE);
            strSaleTypeID = intent.getStringExtra(EXTRA_SALE_TYPE_ID);
            leadId = intent.getStringExtra(EXTRA_LEAD_ID);
        }

       /* if ("to invoice".equalsIgnoreCase(strSaleType)) {
            showTile("To be Invoiced" + "/" + strSaleType);
        } else {*/
        showTile(strSaleType);
        //}

        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSaleOrder(activity, strSaleTypeID, leadId);
    }

    @Override
    public void onSuccessSaleOrder(SaleOrderResponse saleOrderResponse) {
        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);
        if (saleOrderResponse.getRecords() != null) {
            saleOrderAdapter = new SaleOrderAdapter(this, saleOrderResponse.getRecords(), strSaleTypeID);
            recyclerView.setAdapter(saleOrderAdapter);
        }
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

        mSearchView.setQueryHint("Search by order no");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                saleOrderAdapter.filter(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


}
