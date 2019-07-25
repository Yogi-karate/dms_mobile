package com.dealermanagmentsystem.ui.enquiry.enquirycreate;


import com.dealermanagmentsystem.data.model.enquirydetail.EnquiryDetailResponse;
import com.dealermanagmentsystem.data.model.common.Response;

public interface ICreateEnquireView {

    public void onSuccessTypes(Response typesResponse);


    public void onSuccessProduct(Response typesResponse);

    public void onSuccessSource(Response typesResponse);

    public void onSuccessCreateEnquiry(String response);

    public void onError(String message);

    public void onSuccessEnquiryDetail(EnquiryDetailResponse enquiryDetailResponse);

    public void onSuccessEditEnquiryDetail(String message);

    public void onFailEnquiryDetail();
}
