package com.dealermanagmentsystem.ui.saleorder;


import com.dealermanagmentsystem.data.model.saleorder.SaleOrderResponse;

public interface ISaleOrderView {

    public void onSuccessSaleOrder(SaleOrderResponse enquiryResponse);
    public void onError(String message);


}
