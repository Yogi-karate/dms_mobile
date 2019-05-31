package com.dealermanagmentsystem.ui.enquiry.tasks;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.data.model.leadoverview.LeadOverviewResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
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
import static com.dealermanagmentsystem.constants.Constants.STAGE_ID;
import static com.dealermanagmentsystem.constants.ConstantsUrl.ACTIVITY_COMPLETE_FEEDBACK;
import static com.dealermanagmentsystem.constants.ConstantsUrl.MARK_WON_LOST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.TASKS;

public class TasksPresenter implements ITasksPresenter {

    ITasksView view;

    public TasksPresenter(ITasksView iTasksView) {
        view = iTasksView;
    }

    @Override
    public void getTasks(Activity activity, String strLeadId) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(TASKS + strLeadId, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                Gson gson = new Gson();
                String jsonOutput = result.getResponse();
                Type listType = new TypeToken<List<TasksResponse>>() {
                }.getType();
                List<TasksResponse> tasks = gson.fromJson(jsonOutput, listType);
                view.onSuccessTasks(tasks);
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

    @Override
    public void setFeedback(Activity activity, String taskId, String strFeedback) {
        String json = "";
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(ID, taskId);
            postDataParams.put(FEEDBACK, strFeedback);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(ACTIVITY_COMPLETE_FEEDBACK, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    CommonResponse commonResponse = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                    view.onSuccessFeedBack(commonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                if (result.getStatusCode() == BAD_AUTHENTICATION) {
                    view.onError("Wrong username or password");
                } else {
                    view.onError("Something went wrong, Please try after sometime");
                }
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }
}
