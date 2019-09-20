package com.dealermanagmentsystem.ui.insurance.booking;


import com.dealermanagmentsystem.data.model.bookinginsurance.InsuranceBookingResponse;

public interface IInsuranceBookingView {

    public void onSuccessInsuranceBooking(InsuranceBookingResponse enquiryResponse);
    public void onError(String message);


}
