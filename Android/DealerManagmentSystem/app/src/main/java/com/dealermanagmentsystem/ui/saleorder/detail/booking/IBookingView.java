package com.dealermanagmentsystem.ui.saleorder.detail.booking;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.sobooking.SOBookingResponse;

public interface IBookingView {

    public void onSuccessBookingDetails(SOBookingResponse bookingResponse);
    public void onSuccessUpdateBookingDetails(CommonResponse message);
    public void onError(String message);


}
