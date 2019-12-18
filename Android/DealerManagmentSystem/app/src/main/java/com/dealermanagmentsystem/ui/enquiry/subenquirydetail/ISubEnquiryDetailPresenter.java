package com.dealermanagmentsystem.ui.enquiry.subenquirydetail;

import android.app.Activity;

public interface ISubEnquiryDetailPresenter {

    public void getEnquiryDetailResponse(Activity activity, String id);
    public void getLostReason(Activity activity, String id);

    public void getStage(Activity activity, String id);

    public void markWonLost(Activity activity, String id, String isAction, int lostReason);

    public void moveStage(Activity activity, String leadId, int stageId);

}
