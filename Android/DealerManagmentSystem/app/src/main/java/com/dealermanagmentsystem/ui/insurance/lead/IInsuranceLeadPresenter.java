package com.dealermanagmentsystem.ui.insurance.lead;

import android.app.Activity;

public interface IInsuranceLeadPresenter {

    public void getInsuranceLeads(Activity activity, String strState, boolean isCheckedMyLeads);

    public void getLostReason(Activity activity, String id);

    public void markLost(Activity activity, String id, String isAction, int lostReason);

}
