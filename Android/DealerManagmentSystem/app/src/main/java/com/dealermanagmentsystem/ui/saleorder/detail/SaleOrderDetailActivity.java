package com.dealermanagmentsystem.ui.saleorder.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.saleorder.detail.booking.BookingFragment;
import com.dealermanagmentsystem.ui.saleorder.detail.customer.CustomerFragment;
import com.dealermanagmentsystem.ui.saleorder.detail.payment.PaymentFragment;
import com.dealermanagmentsystem.ui.saleorder.detail.price.PriceFragment;
import com.dealermanagmentsystem.utils.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALES_CONSULTANT_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_STATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_NAME;

public class SaleOrderDetailActivity extends BaseActivity {

    Activity activity;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    String strSaleOrderId;
    String strSalesConsultantName;
    String strTeamName;
    String strSaleOrderName;
    String strSaleOrderState;
    @BindView(R.id.sale_order_name)
    TextView saleOrderName;
    @BindView(R.id.team_name)
    TextView teamName;
    @BindView(R.id.sales_consultant_name)
    TextView salesConsultantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order_detail);

        activity = SaleOrderDetailActivity.this;
        ButterKnife.bind(this);
        showTile("Sale Order");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();
        final Intent intent = getIntent();
        if (intent != null) {
            strSaleOrderId = intent.getStringExtra(EXTRA_SALE_ORDER_ID);
            strSalesConsultantName = intent.getStringExtra(EXTRA_SALES_CONSULTANT_NAME);
            strTeamName = intent.getStringExtra(EXTRA_TEAM_NAME);
            strSaleOrderName = intent.getStringExtra(EXTRA_SALE_ORDER_NAME);
            strSaleOrderState = intent.getStringExtra(EXTRA_SALE_ORDER_STATE);
        }

        saleOrderName.setText(strSaleOrderName + " - " + strSaleOrderState);
        teamName.setText(strTeamName);
        salesConsultantName.setText(strSalesConsultantName);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SALE_ORDER_ID, strSaleOrderId);
        bundle.putString(EXTRA_SALES_CONSULTANT_NAME, strSalesConsultantName);
        bundle.putString(EXTRA_TEAM_NAME, strTeamName);
        bundle.putString(EXTRA_SALE_ORDER_NAME, strSaleOrderName);
        bundle.putString(EXTRA_SALE_ORDER_STATE, strSaleOrderState);

        CustomerFragment customerFragment = new CustomerFragment();
        customerFragment.setArguments(bundle);

        PriceFragment priceFragment = new PriceFragment();
        priceFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(customerFragment, "Customer");
        adapter.addFrag(priceFragment, "Price");

        if (!strSaleOrderState.equalsIgnoreCase("draft")) {
            PaymentFragment paymentFragment = new PaymentFragment();
            paymentFragment.setArguments(bundle);

            BookingFragment bookingFragment = new BookingFragment();
            bookingFragment.setArguments(bundle);

            adapter.addFrag(paymentFragment, "Payment");
            adapter.addFrag(bookingFragment, "Booking");
        }

        viewPager.setAdapter(adapter);
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Customer");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Price");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        if (!strSaleOrderState.equalsIgnoreCase("draft")) {
            TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabThree.setText("Payment");
            tabLayout.getTabAt(2).setCustomView(tabThree);

            TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabFour.setText("Booking");
            tabLayout.getTabAt(3).setCustomView(tabFour);

        }


    }

}
