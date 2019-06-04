package com.dealermanagmentsystem.ui.enquiry.tasks;

import android.app.Activity;

public interface ITasksPresenter {

    public void getTasks(Activity activity, String strLeadId);
    public void getTasksOverview(Activity activity);

    public void setFeedback(Activity activity, String taskId, String strFeedback);

}
