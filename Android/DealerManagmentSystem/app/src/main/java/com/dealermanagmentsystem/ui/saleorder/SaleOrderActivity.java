package com.dealermanagmentsystem.ui.saleorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        }

        if ("to invoice".equalsIgnoreCase(strSaleType)) {
            showTile("To be Invoiced" + "/" + strSaleType);
        }else{
            showTile("Sale Order" + "/" + strSaleType);
        }

        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        presenter.getSaleOrder(activity, strSaleTypeID);

    }

    @Override
    public void onSuccessSaleOrder(SaleOrderResponse saleOrderResponse) {
        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);
        if (saleOrderResponse.getRecords() != null) {
            saleOrderAdapter = new SaleOrderAdapter(this, saleOrderResponse.getRecords());
            recyclerView.setAdapter(saleOrderAdapter);
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}
