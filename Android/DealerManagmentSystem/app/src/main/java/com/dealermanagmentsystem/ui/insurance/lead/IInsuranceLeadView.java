package com.dealermanagmentsystem.ui.insurance.lead;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;

public interface IInsuranceLeadView {

    public void onSuccessInsuranceLeads(EnquiryResponse enquiryResponse);
    public void onSuccessLost(CommonResponse enquiryResponse);
    public void onSuccessLostReason(Response response, String id);
    public void onError(String message);


}
