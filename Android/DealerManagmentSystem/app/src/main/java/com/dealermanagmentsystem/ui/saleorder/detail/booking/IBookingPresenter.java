package com.dealermanagmentsystem.ui.saleorder.detail.booking;

import android.app.Activity;

public interface IBookingPresenter {

    public void getBookingDetails(Activity activity, String saleOrderId);

    public void updateBookingDetails(Activity activity, String saleOrderId, String strFinanceTypeId, String strFinancierId, String sFinanceAmount, String sFinancePaymentDate, String sMarginMoneyAmount, String sMarginMoneyDate, String sDeliveryDate);

}
