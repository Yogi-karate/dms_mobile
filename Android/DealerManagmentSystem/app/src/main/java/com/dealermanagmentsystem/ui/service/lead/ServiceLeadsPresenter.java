package com.dealermanagmentsystem.ui.service.lead;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.ACTIVE;
import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.Constants.PROBABILITY;
import static com.dealermanagmentsystem.constants.Constants.SERVICE_LOST_REASON;
import static com.dealermanagmentsystem.constants.ConstantsUrl.GET_SERVICE_LOST_REASON;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LEADS;
import static com.dealermanagmentsystem.constants.ConstantsUrl.MARK_SERVICE_LOST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SERVICE_LEADS;

public class ServiceLeadsPresenter implements IServiceLeadPresenter {

    IServiceLeadView view;

    public ServiceLeadsPresenter(IServiceLeadView iServiceLeadView) {
        view = iServiceLeadView;
    }

    @Override
    public void getServiceLeads(Activity activity, String strState, boolean isCheckedMyLeads) {
        String url;
        if (isCheckedMyLeads) {
            url = SERVICE_LEADS + "?state=" + strState + "&callType=Service" + "&userId=" + DMSPreference.getInt(KEY_USER_ID);
        } else {
            url = SERVICE_LEADS + "?state=" + strState + "&callType=Service";
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(url, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    EnquiryResponse enquiryResponse = gson.fromJson(jsonObject.toString(), EnquiryResponse.class);
                    view.onSuccessServiceLeads(enquiryResponse);
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
    public void getLostReason(Activity activity, final String id) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(GET_SERVICE_LOST_REASON, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessLostReason(response, id);
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
    public void markLost(Activity activity, String id, String isAction, int lostReason) {
        String json = "";

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(PROBABILITY, 0);
            postDataParams.put(ACTIVE, false);
            postDataParams.put(SERVICE_LOST_REASON, lostReason);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(MARK_SERVICE_LOST + id, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    CommonResponse commonResponse = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                    view.onSuccessLost(commonResponse);
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
