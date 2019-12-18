package com.dealermanagmentsystem.ui.saleorder.detail.customer;

import android.app.Activity;

public interface ICustomerPresenter {

    public void getCustomer(Activity activity, String saleOrderId);

    public void confirmQuotation(Activity activity, String saleOrderId);

}
