package com.dealermanagmentsystem.ui.saleorder.detail.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.socustomer.Record;
import com.dealermanagmentsystem.data.model.socustomer.SOCustomerResponse;
import com.dealermanagmentsystem.event.LeadActionWonEvent;
import com.dealermanagmentsystem.ui.base.BaseApplication;
import com.dealermanagmentsystem.ui.base.BaseFragment;
import com.dealermanagmentsystem.ui.saleorder.detail.SaleOrderDetailActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALES_CONSULTANT_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_STATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_NAME;


public class CustomerFragment extends BaseFragment implements ICustomerView {


    @BindView(R.id.customer_name)
    TextView customerName;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.delivery_date)
    TextView deliveryDate;
    @BindView(R.id.model_name)
    TextView modelName;
    @BindView(R.id.variant_name)
    TextView variantName;
    @BindView(R.id.color_name)
    TextView colorName;
    @BindView(R.id.warehouse_name)
    TextView warehouseName;
    @BindView(R.id.confirm)
    Button btnConfirm;

    Activity activity;
    CustomerPresenter presenter;

    String strSaleOrderId;
    String strSaleOrderState;
    String strSalesConsultantName;
    String strTeamName;
    String strSaleOrderName;

    public CustomerFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_customer, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        strSaleOrderId = getArguments().getString(EXTRA_SALE_ORDER_ID);
        strSaleOrderState = getArguments().getString(EXTRA_SALE_ORDER_STATE);
        strSalesConsultantName = getArguments().getString(EXTRA_SALES_CONSULTANT_NAME);
        strTeamName = getArguments().getString(EXTRA_TEAM_NAME);
        strSaleOrderName = getArguments().getString(EXTRA_SALE_ORDER_NAME);

        presenter = new CustomerPresenter(this);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getCustomer(activity, strSaleOrderId);
        if (!strSaleOrderState.equalsIgnoreCase("draft")) {
            btnConfirm.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.confirm) //ButterKnife uses.
    public void confirmQuotation() {
        presenter.confirmQuotation(activity, strSaleOrderId);
    }

    @Override
    public void onSuccessCustomer(SOCustomerResponse soCustomerResponse) {
        final Record record = soCustomerResponse.getRecords().get(0);

        if (record != null) {
            String strPartnerName = "";
            String strWarehouse = "";
            final Object partnerId = record.getPartnerId();
            if (partnerId instanceof List) {
                strPartnerName = String.valueOf(((List) partnerId).get(1));
            }

            final Object warehouseId = record.getWarehouseId();
            if (warehouseId instanceof List) {
                strWarehouse = String.valueOf(((List) warehouseId).get(1));
            }

            customerName.setText(strPartnerName);
            warehouseName.setText(strWarehouse);
            orderDate.setText(record.getDateOrder());
            deliveryDate.setText(record.getDeliveryDate());
            modelName.setText(record.getProductName());
            variantName.setText(record.getProductVariant());
            colorName.setText(record.getProductColor());
        }

    }

    @Override
    public void onSuccessConfirmQuotation(String message) {
        if (message.equalsIgnoreCase("success")) {
            DMSToast.showLong(activity, "Quotation Confirmed");
            btnConfirm.setVisibility(View.GONE);

            strSaleOrderState = "sale";
            Intent intent = new Intent(activity, SaleOrderDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(EXTRA_SALE_ORDER_ID, strSaleOrderId);
            intent.putExtra(EXTRA_TEAM_NAME, strTeamName);
            intent.putExtra(EXTRA_SALES_CONSULTANT_NAME, strSalesConsultantName);
            intent.putExtra(EXTRA_SALE_ORDER_NAME, strSaleOrderName);
            intent.putExtra(EXTRA_SALE_ORDER_STATE, strSaleOrderState);
            activity.startActivity(intent);

            /*final Intent intent = activity.getIntent();
            intent.putExtra(EXTRA_SALE_ORDER_ID, strSaleOrderId);
            intent.putExtra(EXTRA_TEAM_NAME, strTeamName);
            intent.putExtra(EXTRA_SALES_CONSULTANT_NAME, strSalesConsultantName);
            intent.putExtra(EXTRA_SALE_ORDER_NAME, strSaleOrderName);
            intent.putExtra(EXTRA_SALE_ORDER_STATE, strSaleOrderState);
            activity.recreate();*/
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }


}
