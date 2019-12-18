package com.dealermanagmentsystem.ui.saleorder.detail.price;


import com.dealermanagmentsystem.data.model.socustomer.SOCustomerResponse;
import com.dealermanagmentsystem.data.model.soprice.SOPriceResponse;

public interface IPriceView {

    public void onSuccessPrice(SOPriceResponse soPriceResponse);
    public void onError(String message);


}
