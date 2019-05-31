package com.dealermanagmentsystem.ui.enquiry.tasks;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.tasks.ActivityTypeResponse;

public interface ITasksCreateView {

    public void onSuccessCreateTasks(CommonResponse response);
    public void onSuccessActivityType(ActivityTypeResponse response);
    public void onSuccessUsers(ActivityTypeResponse response);
    public void onError(String message);


}
