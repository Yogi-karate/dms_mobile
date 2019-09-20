package com.dealermanagmentsystem.ui.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.PaymentAdapter;

import com.dealermanagmentsystem.data.model.payment.PaymentDetailResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentDetailActivity extends BaseActivity {
    Activity activity;
    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    PaymentDetailResponse paymentDetailResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        activity = PaymentDetailActivity.this;
        ButterKnife.bind(this);

        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Payments");
        showBackButton();

        final Intent intent = getIntent();
        if (intent != null) {
            paymentDetailResponse = (PaymentDetailResponse) intent.getSerializableExtra("PaymentList");
        }

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        if (paymentDetailResponse.getRecords() != null) {
            PaymentAdapter  paymentAdapter = new PaymentAdapter(this, paymentDetailResponse.getRecords());
            recyclerView.setAdapter(paymentAdapter);
        }
    }

}
