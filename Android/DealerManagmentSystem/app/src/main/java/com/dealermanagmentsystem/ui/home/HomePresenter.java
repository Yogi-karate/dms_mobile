package com.dealermanagmentsystem.ui.home;

import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.leadoverview.LeadOverviewResponse;
import com.dealermanagmentsystem.data.model.login.LoginResponse;
import com.dealermanagmentsystem.data.model.saleorder.saleoverview.SaleOverviewResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.FCM_TOKEN_PARAM;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.KEY_FCM_TOKEN_SET;
import static com.dealermanagmentsystem.constants.Constants.MOBILE;
import static com.dealermanagmentsystem.constants.Constants.PASSWORD;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LEAD_OVERVIEW;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LOGIN;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALES_OVERVIEW;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SEND_FCM_TOKEN;
import static com.dealermanagmentsystem.constants.ConstantsUrl.TASKS;
import static com.dealermanagmentsystem.constants.ConstantsUrl.TASKS_OVERVIEW;

public class HomePresenter implements IHomePresenter {

    IHomeView view;

    public HomePresenter(IHomeView iHomeView) {
        view = iHomeView;
    }

    @Override
    public void getLeadsOverview(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(LEAD_OVERVIEW, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                try {
                    Gson gson = new Gson();
                    String jsonOutput = result.getResponse();
                    Type listType = new TypeToken<List<LeadOverviewResponse>>() {
                    }.getType();
                    List<LeadOverviewResponse> posts = gson.fromJson(jsonOutput, listType);
                    view.onSuccessLeadOverview(posts);
                }catch (Exception e){
                    view.onError("Something went wrong, Please try after sometime");
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
    public void getSalesOverview(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SALES_OVERVIEW, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                try {
                    Gson gson = new Gson();
                    String jsonOutput = result.getResponse();
                    Type listType = new TypeToken<List<SaleOverviewResponse>>() {
                    }.getType();
                    List<SaleOverviewResponse> posts = gson.fromJson(jsonOutput, listType);
                    view.onSuccessSalesOverview(posts);
                }catch (Exception e){
                    view.onError("Something went wrong, Please try after sometime");
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
    public void getTasksOverview(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(TASKS_OVERVIEW , activity, GET, new IConnectionListener() {
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
    public void sendFcmToken(Activity activity, String token) {

        String json = "";
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(FCM_TOKEN_PARAM, token);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SEND_FCM_TOKEN, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                DMSPreference.setBoolean(KEY_FCM_TOKEN_SET, true);
                view.onSuccessToken();
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
