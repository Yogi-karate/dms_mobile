package com.dealermanagmentsystem.ui.home;

import android.app.Activity;

public interface IHomePresenter {

    public void getLeadsOverview(Activity activity);

    public void getSalesOverview(Activity activity);

    public void getTasksOverview(Activity activity);

    public void setFeedback(Activity activity, String taskId, String strFeedback);

    public void getDeliveryCount(Activity activity);

    public void getInvoiceCount(Activity activity);

    public void sendFcmToken(Activity activity, String token);

    public void getTeamDetailList(Activity activity, String teamId);

    public void getPaymentDetails(Activity activity);

    public void getServiceLeadsOverview(Activity activity);

    public void getServiceBookingCount(Activity activity);

    public void getInsuranceLeadsOverview(Activity activity);

    public void getInsuranceBookingCount(Activity activity);

    public void getAppUpdate(Activity activity);
}
