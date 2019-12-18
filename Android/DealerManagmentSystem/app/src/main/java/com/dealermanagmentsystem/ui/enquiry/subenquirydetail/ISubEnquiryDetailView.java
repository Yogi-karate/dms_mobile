package com.dealermanagmentsystem.ui.enquiry.subenquirydetail;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.subenquirydetail.SubEnquiryDetailResponse;

public interface ISubEnquiryDetailView {

    public void onSuccessResponse(SubEnquiryDetailResponse subEnquiryDetailResponse);
    public void onError(String message);
    public void onSuccessWonLost(CommonResponse enquiryResponse);
    public void onSuccessLostReason(Response response, String id);
    public void onSuccessGetStages(Response response, String id);

}
