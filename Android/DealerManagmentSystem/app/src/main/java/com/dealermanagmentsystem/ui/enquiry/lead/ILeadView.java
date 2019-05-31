package com.dealermanagmentsystem.ui.enquiry.lead;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;

public interface ILeadView {

    public void onSuccessLeads(EnquiryResponse enquiryResponse);
    public void onSuccessWonLost(CommonResponse enquiryResponse);
    public void onSuccessLostReason(Response response, String id);
    public void onSuccessGetStages(Response response, String id);
    public void onError(String message);


}
