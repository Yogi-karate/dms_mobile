package com.dealermanagmentsystem.ui.userenquiry;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.data.model.userdetail.UserDetailResponse;

import java.util.List;

public interface IUserDetailView {

    public void onSuccess(List<UserDetailResponse> tasks);
    public void onError(String message);


}
