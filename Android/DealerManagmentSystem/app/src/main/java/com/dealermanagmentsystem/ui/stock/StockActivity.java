package com.dealermanagmentsystem.ui.stock;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.StockAdapter;
import com.dealermanagmentsystem.data.model.stock.StockResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockActivity extends BaseActivity implements IStockView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    StockPresenter presenter;
    StockAdapter stockAdapter;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order);

        activity = StockActivity.this;
        ButterKnife.bind(this);

        presenter = new StockPresenter(this);

        showTile("Stock");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();
        presenter.getStock(activity);
    }

    @Override
    public void onSuccessStock(StockResponse stockResponse) {

        if (TextUtils.isEmpty(stockResponse.getError())) {
            showTile("Stock - " + String.valueOf(stockResponse.getRecords().size()));

            GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(gridLayoutManagerCategories);
            if (stockResponse.getRecords() != null) {
                stockAdapter = new StockAdapter(this, stockResponse.getRecords());
                recyclerView.setAdapter(stockAdapter);
            }
        } else {
            onError("Could not get Vehicle stock, Please try after sometime");
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

        mSearchView.setQueryHint("Search by engine no. or variant or model");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stockAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
