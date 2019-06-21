package com.dealermanagmentsystem.ui.home;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.leadoverview.LeadOverviewResponse;
import com.dealermanagmentsystem.data.model.saleorder.saleoverview.SaleOverviewResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;

import java.util.List;

public interface IHomeView {

    public void onSuccessLeadOverview(List<LeadOverviewResponse> leadOverviewResponse);
    public void onSuccessSalesOverview(List<SaleOverviewResponse> saleOverviewResponses);
    public void onSuccessTasks(List<TasksResponse> tasksResponses);
    public void onSuccessDeliveryCount(String count);
    public void onSuccessInvoiceCount(String count);

    public void onError(String message);
    public void onSuccessToken();
    public void onSuccessFeedBack(CommonResponse commonResponse);

}
