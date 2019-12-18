package com.dealermanagmentsystem.ui.quotation;

import android.app.Activity;

public interface IQuotationCreatePresenter {

    public void getPriceList(Activity activity);
    public void createQuotation(Activity activity, int productId, int variantId, int colorId, int priceListId, int strId, String strMobile, String strName, String strEmail, int strTeamId, int strUserId);

}
