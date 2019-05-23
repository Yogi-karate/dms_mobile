package com.dealermanagmentsystem.ui.enquiry.lead;

import android.app.Activity;

public interface ILeadPresenter {

    public void getLeads(Activity activity, String strState, String strStage);

    public void getLostReason(Activity activity, String id);

    public void getStage(Activity activity, String id);

    public void markWonLost(Activity activity, String id, String isAction, int lostReason);

    public void moveStage(Activity activity, String leadId, int stageId);

}
