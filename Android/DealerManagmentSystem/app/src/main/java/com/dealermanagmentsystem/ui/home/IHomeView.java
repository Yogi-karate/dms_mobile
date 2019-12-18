package com.dealermanagmentsystem.ui.home;


import com.dealermanagmentsystem.data.model.appupdate.AppUpdateResponse;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.leadoverview.LeadOverviewResponse;
import com.dealermanagmentsystem.data.model.login.LoginResponse;
import com.dealermanagmentsystem.data.model.payment.PaymentDetailResponse;
import com.dealermanagmentsystem.data.model.saleorder.saleoverview.SaleOverviewResponse;
import com.dealermanagmentsystem.data.model.serviceoverview.ServiceLeadOverviewResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.data.model.teamdetail.TeamDetailResponse;

import java.util.List;

public interface IHomeView {

    public void onSuccessLeadOverview(List<LeadOverviewResponse> leadOverviewResponse);
    public void onSuccessSalesOverview(List<SaleOverviewResponse> saleOverviewResponses);
    public void onSuccessTasks(List<TasksResponse> tasksResponses);
    public void onSuccessDeliveryCount(String count);
    public void onSuccessToBeBookedCount(String count);
    public void onSuccessBookedCount(String count);
    public void onSuccessStockCount(String count);
    public void onSuccessQuotationCount(String count);

    public void onError(String message);
    public void onSuccessToken();
    public void onSuccessFeedBack(CommonResponse commonResponse);

    public void onSuccessTeamDetail(List<TeamDetailResponse> commonResponse);

    public void onSuccessPaymentDetail(PaymentDetailResponse commonResponse);

    public void onSuccessServiceLeadOverview(List<ServiceLeadOverviewResponse> response);

    public void onSuccessServiceBookingCount(List<ServiceLeadOverviewResponse> response);

    public void onSuccessInsuranceLeadOverview(List<ServiceLeadOverviewResponse> response);

    public void onSuccessInsuranceBookingCount(List<ServiceLeadOverviewResponse> response);

    public void onSuccessAppUpdate(AppUpdateResponse response);

    public void onSuccessSetCompany(LoginResponse response, String id, String name);

}
