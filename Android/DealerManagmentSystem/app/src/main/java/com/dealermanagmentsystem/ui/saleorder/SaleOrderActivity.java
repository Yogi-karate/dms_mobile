package com.dealermanagmentsystem.ui.saleorder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.EnquiryAdapter;
import com.dealermanagmentsystem.adapter.SaleOrderAdapter;
import com.dealermanagmentsystem.data.model.saleorder.SaleOrderResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleOrderActivity extends BaseActivity implements  ISaleOrderView{

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    SaleOrderPresenter presenter;
    SaleOrderAdapter saleOrderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order);

        activity = SaleOrderActivity.this;
        ButterKnife.bind(this);

        presenter = new SaleOrderPresenter(this);

        showTile("Sale Order");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        presenter.getSaleOrder(activity);

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
