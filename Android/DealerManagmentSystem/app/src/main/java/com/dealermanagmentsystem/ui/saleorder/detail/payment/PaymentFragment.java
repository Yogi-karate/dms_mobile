package com.dealermanagmentsystem.ui.saleorder.detail.payment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SoPaymentAdapter;
import com.dealermanagmentsystem.data.model.sopayment.Record;
import com.dealermanagmentsystem.data.model.sopayment.SOPaymentResponse;
import com.dealermanagmentsystem.ui.base.BaseFragment;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_ID;


public class PaymentFragment extends BaseFragment implements IPaymentView {
    String strSaleOrderId;
    Activity activity;

    PaymentPresenter presenter;
    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        strSaleOrderId = getArguments().getString(EXTRA_SALE_ORDER_ID);
        presenter = new PaymentPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getPaymentList(activity, strSaleOrderId);
    }

    @Override
    public void onSuccessPaymentList(SOPaymentResponse soPaymentResponse) {
        final List<Record> records = soPaymentResponse.getRecords();
        if (!records.isEmpty()) {
            GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(activity, 1);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(gridLayoutManagerCategories);

            SoPaymentAdapter soPaymentAdapter = new SoPaymentAdapter(records);
            recyclerView.setAdapter(soPaymentAdapter);
        }

    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}
