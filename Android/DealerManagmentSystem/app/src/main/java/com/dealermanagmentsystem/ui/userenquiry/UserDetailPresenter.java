package com.dealermanagmentsystem.ui.userenquiry;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.data.model.userdetail.UserDetailResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.ui.enquiry.tasks.ITasksPresenter;
import com.dealermanagmentsystem.ui.enquiry.tasks.ITasksView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.FEEDBACK;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.ID;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.ACTIVITY_COMPLETE_FEEDBACK;
import static com.dealermanagmentsystem.constants.ConstantsUrl.TASKS;
import static com.dealermanagmentsystem.constants.ConstantsUrl.TASKS_OVERVIEW;
import static com.dealermanagmentsystem.constants.ConstantsUrl.USER_DETAIL;

public class UserDetailPresenter implements IUserDetailPresenter {

    IUserDetailView view;

    public UserDetailPresenter(IUserDetailView iUserDetailView) {
        view = iUserDetailView;
    }

    @Override
    public void getUserDetail(Activity activity, String id) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(USER_DETAIL + id,
                activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                Gson gson = new Gson();
                String jsonOutput = result.getResponse();
                Type listType = new TypeToken<List<UserDetailResponse>>() {
                }.getType();
                List<UserDetailResponse> responses = gson.fromJson(jsonOutput, listType);
                view.onSuccess(responses);
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }
}
