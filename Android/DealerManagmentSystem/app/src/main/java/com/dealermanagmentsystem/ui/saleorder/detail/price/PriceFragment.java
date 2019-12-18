package com.dealermanagmentsystem.ui.saleorder.detail.price;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SoPriceAdapter;
import com.dealermanagmentsystem.data.model.soprice.Record;
import com.dealermanagmentsystem.data.model.soprice.SOPriceResponse;
import com.dealermanagmentsystem.data.model.soprice.SaleOrder;
import com.dealermanagmentsystem.ui.base.BaseFragment;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_ID;


public class PriceFragment extends BaseFragment implements IPriceView {
    String strSaleOrderId;
    Activity activity;
    @BindView(R.id.untaxed_amount)
    TextView untaxedAmount;
    @BindView(R.id.taxes)
    TextView taxes;
    @BindView(R.id.total)
    TextView total;
    PricePresenter presenter;
    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;

    public PriceFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_price, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        strSaleOrderId = getArguments().getString(EXTRA_SALE_ORDER_ID);
        presenter = new PricePresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getPrice(activity, strSaleOrderId);
    }

    @Override
    public void onSuccessPrice(SOPriceResponse soPriceResponse) {
        final SaleOrder saleorder = soPriceResponse.getSaleorder();
        untaxedAmount.setText(String.valueOf(saleorder.getAmountUntaxed())+" \u20B9" );
        taxes.setText(String.valueOf(saleorder.getAmountTax())+" \u20B9");
        total.setText(String.valueOf(saleorder.getAmountTotal())+" \u20B9");

        final List<Record> records = soPriceResponse.getRecords();

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(activity, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        SoPriceAdapter soPriceAdapter = new SoPriceAdapter(records);
        recyclerView.setAdapter(soPriceAdapter);

    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}
