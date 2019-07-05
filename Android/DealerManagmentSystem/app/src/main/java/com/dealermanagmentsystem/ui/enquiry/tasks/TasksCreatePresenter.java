package com.dealermanagmentsystem.ui.enquiry.tasks;


import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.tasks.ActivityTypeResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.ACTIVITY_TYPE_ID;
import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.DATE_DEADLINE;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.NOTE;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.Constants.RES_ID;
import static com.dealermanagmentsystem.constants.Constants.SUMMARY;
import static com.dealermanagmentsystem.constants.Constants.USER_ID;
import static com.dealermanagmentsystem.constants.ConstantsUrl.ACTIVITY_TYPE;
import static com.dealermanagmentsystem.constants.ConstantsUrl.CREATE_ACTIVITY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.EDIT_ACTIVITY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.USERS;

public class TasksCreatePresenter implements ITasksCreatePresenter {

    ITasksCreateView view;

    public TasksCreatePresenter(ITasksCreateView iTasksCreateView) {
        view = iTasksCreateView;
    }


    @Override
    public void createTask(Activity activity, String strSummary, String strNote,
                           int userId, int activityTypeId, String strFollowUpDate, int leadId) {
        /*if (userId == -1) {
            view.onError("please select a assignee");
        } else*/ if (activityTypeId == -1) {
            view.onError("please select a activity type");
        } else if (TextUtils.isEmpty(strSummary)) {
            view.onError("please enter a summary");
        } else if (TextUtils.isEmpty(strNote)) {
            view.onError("please enter a note");
        } else if (TextUtils.isEmpty(strFollowUpDate)) {
            view.onError("please select a follow up date");
        } else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(RES_ID, leadId);
                postDataParams.put(DATE_DEADLINE, strFollowUpDate);
                if (userId != -1){
                    postDataParams.put(USER_ID, userId);
                }
                postDataParams.put(NOTE, strNote);
                postDataParams.put(ACTIVITY_TYPE_ID, activityTypeId);
                postDataParams.put(SUMMARY, strSummary);
                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CREATE_ACTIVITY, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        Gson gson = new Gson();
                        CommonResponse response = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                        view.onSuccessCreateTasks(response);
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

    @Override
    public void editTask(Activity activity, String strSummary, String strFollowUpDate, String activityId) {
        if (TextUtils.isEmpty(strSummary)) {
            view.onError("please enter a summary");
        } else if (TextUtils.isEmpty(strFollowUpDate)) {
            view.onError("please select a follow up date");
        } else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(DATE_DEADLINE, strFollowUpDate);
                postDataParams.put(SUMMARY, strSummary);
                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(EDIT_ACTIVITY +  activityId, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        Gson gson = new Gson();
                        CommonResponse response = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                        view.onSuccessCreateTasks(response);
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

    @Override
    public void getUsers(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(USERS, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    ActivityTypeResponse response = gson.fromJson(jsonObject.toString(), ActivityTypeResponse.class);
                    view.onSuccessUsers(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    public void getActivityType(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(ACTIVITY_TYPE, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    ActivityTypeResponse response = gson.fromJson(jsonObject.toString(), ActivityTypeResponse.class);
                    view.onSuccessActivityType(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
