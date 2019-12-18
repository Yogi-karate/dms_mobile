package com.dealermanagmentsystem.ui.saleorder.detail.payment;


import com.dealermanagmentsystem.data.model.sopayment.SOPaymentResponse;

public interface IPaymentView {

    public void onSuccessPaymentList(SOPaymentResponse soPaymentResponse);
    public void onError(String message);


}
