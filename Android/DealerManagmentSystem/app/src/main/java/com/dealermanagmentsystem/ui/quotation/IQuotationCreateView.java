package com.dealermanagmentsystem.ui.quotation;


import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.stock.StockResponse;

public interface IQuotationCreateView {

    public void onQuotationSuccess();
    public void onPriceListSuccess(Response enquiryResponse);
    public void onError(String message);


}
