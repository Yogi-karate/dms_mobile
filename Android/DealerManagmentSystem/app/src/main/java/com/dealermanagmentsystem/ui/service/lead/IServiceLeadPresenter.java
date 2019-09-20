package com.dealermanagmentsystem.ui.service.lead;

import android.app.Activity;

public interface IServiceLeadPresenter {

    public void getServiceLeads(Activity activity, String strState, boolean isCheckedMyLeads);

    public void getLostReason(Activity activity, String id);

    public void markLost(Activity activity, String id, String isAction, int lostReason);

}
