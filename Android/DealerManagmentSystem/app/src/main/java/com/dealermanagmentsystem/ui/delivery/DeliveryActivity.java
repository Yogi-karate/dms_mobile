package com.dealermanagmentsystem.ui.delivery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.DeliveryAdapter;
import com.dealermanagmentsystem.adapter.SaleOrderAdapter;
import com.dealermanagmentsystem.data.model.delivery.DeliveryResponse;
import com.dealermanagmentsystem.data.model.delivery.Record;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DeliveryActivity extends BaseActivity implements IDeliveryView{
    Activity activity;
    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    DeliveryPresenter presenter;
    DeliveryAdapter deliveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        activity = DeliveryActivity.this;
        ButterKnife.bind(this);


        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("To be Allocated");
        showBackButton();

        presenter = new DeliveryPresenter(this);
        presenter.getDelivery(activity);
    }

    @Override
    public void onSuccessDelivery(DeliveryResponse deliveryResponse) {
        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);
        final List<Record> records = deliveryResponse.getRecords();
        if (records != null) {
            deliveryAdapter = new DeliveryAdapter(this, records);
            recyclerView.setAdapter(deliveryAdapter);
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}
