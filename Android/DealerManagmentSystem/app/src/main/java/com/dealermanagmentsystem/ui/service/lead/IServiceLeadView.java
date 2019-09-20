package com.dealermanagmentsystem.ui.service.lead;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;

public interface IServiceLeadView {

    public void onSuccessServiceLeads(EnquiryResponse enquiryResponse);
    public void onSuccessLost(CommonResponse enquiryResponse);
    public void onSuccessLostReason(Response response, String id);
    public void onError(String message);


}
