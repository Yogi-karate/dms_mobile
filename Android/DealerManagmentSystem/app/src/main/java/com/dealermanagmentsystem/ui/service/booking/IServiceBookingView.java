package com.dealermanagmentsystem.ui.service.booking;


import com.dealermanagmentsystem.data.model.bookingservice.ServiceBookingResponse;

public interface IServiceBookingView {

    public void onSuccessServiceBooking(ServiceBookingResponse enquiryResponse);
    public void onError(String message);


}
