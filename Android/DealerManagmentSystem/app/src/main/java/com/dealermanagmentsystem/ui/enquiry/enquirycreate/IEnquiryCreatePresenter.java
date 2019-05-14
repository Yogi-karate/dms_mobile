package com.dealermanagmentsystem.ui.enquiry.enquirycreate;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.enquirydetail.EnquiryDetailResponse;
import com.dealermanagmentsystem.data.model.enquirydetail.EnquiryEditRequest;

import java.util.List;

public interface IEnquiryCreatePresenter {

    public void getTypes(Activity activity);

    public void getProduct(Activity activity);

    public void getSource(Activity activity);

    public void createEnquiry(Activity activity, List<Integer> typeListId,
                              int productId, int sourceId, String strFollowUpDate,
                                String strName, String strMobileNo, String strMail);

    public void getEnquiryDetail(Activity activity, String stringExtra);

    public void editEnquiry(Activity activity, EnquiryEditRequest enquiryDetailResponse, EnquiryDetailResponse detailResponse);
}
