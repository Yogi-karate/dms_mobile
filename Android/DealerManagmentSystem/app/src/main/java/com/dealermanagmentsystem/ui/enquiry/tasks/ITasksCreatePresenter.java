package com.dealermanagmentsystem.ui.enquiry.tasks;

import android.app.Activity;

public interface ITasksCreatePresenter {

    public void createTask(Activity activity, String strSummary, String strNote, int userId, int activityTypeId, String strFollowUpDate, int leadId);

    public void createServiceTask(Activity activity, String strSummary, String strNote, int userId, int activityTypeId, String strFollowUpDate, int leadId);

    public void editTask(Activity activity, String strSummary, String strFollowUpDate, String activityId);

    public void getUsers(Activity activity);

    public void getActivityType(Activity activity);

}
