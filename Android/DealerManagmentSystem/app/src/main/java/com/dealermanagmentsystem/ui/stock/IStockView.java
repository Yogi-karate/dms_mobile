package com.dealermanagmentsystem.ui.stock;


import com.dealermanagmentsystem.data.model.stock.StockResponse;

public interface IStockView {

    public void onSuccessStock(StockResponse enquiryResponse);
    public void onError(String message);


}
