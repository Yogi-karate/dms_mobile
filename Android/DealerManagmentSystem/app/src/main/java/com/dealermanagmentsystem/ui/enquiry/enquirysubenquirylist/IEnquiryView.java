package com.dealermanagmentsystem.ui.enquiry.enquirysubenquirylist;


import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;

public interface IEnquiryView {

    public void onSuccessEnquiry(EnquiryResponse enquiryResponse);
    public void onError(String message);


}
