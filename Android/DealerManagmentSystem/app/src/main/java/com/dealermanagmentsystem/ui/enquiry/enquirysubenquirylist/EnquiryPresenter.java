package com.dealermanagmentsystem.ui.enquiry.enquirysubenquirylist;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.ENQUIRY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SUB_ENQUIRY;

public class EnquiryPresenter implements IEnquiryPresenter {

    IEnquiryView view;

    public EnquiryPresenter(IEnquiryView iEnquiryView) {
        view = iEnquiryView;
    }

    @Override
    public void getEnquiry(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(ENQUIRY, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    EnquiryResponse enquiryResponse = gson.fromJson(jsonObject.toString(), EnquiryResponse.class);
                    view.onSuccessEnquiry(enquiryResponse);
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
    public void getSubEnquiry(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SUB_ENQUIRY, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    EnquiryResponse enquiryResponse = gson.fromJson(jsonObject.toString(), EnquiryResponse.class);
                    view.onSuccessEnquiry(enquiryResponse);
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
