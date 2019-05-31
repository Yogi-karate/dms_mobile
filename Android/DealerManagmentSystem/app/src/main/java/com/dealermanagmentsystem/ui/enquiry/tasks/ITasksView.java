package com.dealermanagmentsystem.ui.enquiry.tasks;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;

import java.util.List;

public interface ITasksView {

    public void onSuccessTasks(List<TasksResponse> tasks);
    public void onSuccessFeedBack(CommonResponse commonResponse);
    public void onError(String message);


}
