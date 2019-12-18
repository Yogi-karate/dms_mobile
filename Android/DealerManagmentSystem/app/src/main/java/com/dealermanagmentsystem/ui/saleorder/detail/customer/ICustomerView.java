package com.dealermanagmentsystem.ui.saleorder.detail.customer;


import com.dealermanagmentsystem.data.model.socustomer.SOCustomerResponse;

public interface ICustomerView {

    public void onSuccessCustomer(SOCustomerResponse soCustomerResponse);
    public void onSuccessConfirmQuotation(String message);
    public void onError(String message);


}
