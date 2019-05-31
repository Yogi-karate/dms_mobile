package com.dealermanagmentsystem.ui.enquiry.lead;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.ACTIVE;
import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.LOST_REASON;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.Constants.PROBABILITY;
import static com.dealermanagmentsystem.constants.Constants.STAGE_ID;
import static com.dealermanagmentsystem.constants.ConstantsUrl.GET_LOST_REASON;
import static com.dealermanagmentsystem.constants.ConstantsUrl.GET_STAGE;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LEADS;
import static com.dealermanagmentsystem.constants.ConstantsUrl.MARK_WON_LOST;

public class LeadsPresenter implements ILeadPresenter {

    ILeadView view;

    public LeadsPresenter(ILeadView iLeadView) {
        view = iLeadView;
    }

    @Override
    public void getLeads(Activity activity, String strState, String strStage) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(LEADS + "?state=" + strState + "&stage=" + strStage, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    EnquiryResponse enquiryResponse = gson.fromJson(jsonObject.toString(), EnquiryResponse.class);
                    view.onSuccessLeads(enquiryResponse);
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
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(GET_LOST_REASON, activity, GET, new IConnectionListener() {
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
    public void getStage(Activity activity, final String id) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(GET_STAGE, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessGetStages(response, id);
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
    public void markWonLost(Activity activity, String id, String isAction, int lostReason) {
        String json = "";
        if (isAction.equalsIgnoreCase("Won")) {
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(PROBABILITY, 100);
                postDataParams.put(STAGE_ID, 4);
                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(PROBABILITY, 0);
                postDataParams.put(ACTIVE, false);
                postDataParams.put(LOST_REASON, lostReason);
                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(MARK_WON_LOST + id, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    CommonResponse commonResponse = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                    view.onSuccessWonLost(commonResponse);
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

    @Override
    public void moveStage(Activity activity, String leadId, int stageId) {
        String json = "";

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(STAGE_ID, stageId);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(MARK_WON_LOST + leadId, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    CommonResponse commonResponse = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                    view.onSuccessWonLost(commonResponse);
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
