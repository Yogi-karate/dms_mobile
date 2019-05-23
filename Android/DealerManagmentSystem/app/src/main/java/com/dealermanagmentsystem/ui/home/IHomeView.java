package com.dealermanagmentsystem.ui.home;


import com.dealermanagmentsystem.data.model.leadoverview.LeadOverviewResponse;

import java.util.List;

public interface IHomeView {

    public void onSuccessLeadOverview(List<LeadOverviewResponse> leadOverviewResponse);
    public void onError(String message);


}
