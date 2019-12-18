package com.dealermanagmentsystem.ui.saleorder;

import android.app.Activity;

public interface ISaleOrderEditPresenter {

    public void postSaleOrderEdit(Activity activity, String sPaymentDate, String sDeliveryDate, String financeType, String financier, String id);

}
