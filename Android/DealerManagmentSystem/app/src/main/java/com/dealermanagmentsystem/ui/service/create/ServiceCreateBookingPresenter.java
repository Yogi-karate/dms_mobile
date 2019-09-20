package com.dealermanagmentsystem.ui.service.create;

import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.BOOKING_TYPE;
import static com.dealermanagmentsystem.constants.Constants.DOP;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.KEY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_NAME;
import static com.dealermanagmentsystem.constants.Constants.LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.LOCATION_ID;
import static com.dealermanagmentsystem.constants.Constants.PICK_UP_ADDRESS;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.Constants.REMARKS;
import static com.dealermanagmentsystem.constants.Constants.SERVICE_TYPE;
import static com.dealermanagmentsystem.constants.ConstantsUrl.CREATE_SERVICE_BOOKING;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SERVICE_LOCATION;

public class ServiceCreateBookingPresenter implements IServiceCreateBookingPresenter {

    IServiceCreateBookingView view;

    public ServiceCreateBookingPresenter(IServiceCreateBookingView iServiceCreateBookingView) {
        view = iServiceCreateBookingView;
    }

    @Override
    public void createServiceBooking(Activity activity, int locationId, String strBookingTypeId,
                                     String strDop, String strAddress, String strRemarks, String strLeadId, String strServiceTypeId) {

        if (TextUtils.isEmpty(strDop)) {
            view.onError("please select a appointment date and time");
        } else if (TextUtils.isEmpty(strAddress) && strBookingTypeId.equalsIgnoreCase("pickup")) {
            view.onError("please enter a pick up address");
        } /*else if (TextUtils.isEmpty(strRemarks)) {
            view.onError("please enter a remarks");
        }*/ else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                if (strBookingTypeId.equalsIgnoreCase("pickup")) {
                    postDataParams.put(DOP, strDop);
                    postDataParams.put(PICK_UP_ADDRESS, strAddress);
                }
                postDataParams.put(LOCATION_ID, locationId);
                postDataParams.put(SERVICE_TYPE, strServiceTypeId);
                postDataParams.put(REMARKS, strRemarks);
                postDataParams.put(BOOKING_TYPE, strBookingTypeId);
                postDataParams.put(LEAD_ID, strLeadId);
                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CREATE_SERVICE_BOOKING, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        Gson gson = new Gson();
                        CommonResponse response = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                        view.onSuccessServiceCreateBooking(response);
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
    public void getBookingType() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(KEY_NAME, "Pick up");
        hashMap.put(KEY_ID, "pickup");
        list.add(hashMap);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put(KEY_NAME, "Walk in");
        hashMap1.put(KEY_ID, "walk");
        list.add(hashMap1);

        view.onSuccessBookingType(list);
    }

    @Override
    public void getServiceType() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(KEY_NAME, "First Free Service");
        hashMap.put(KEY_ID, "first");
        list.add(hashMap);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put(KEY_NAME, "Second Free Service");
        hashMap1.put(KEY_ID, "second");
        list.add(hashMap1);

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put(KEY_NAME, "Third Free Service");
        hashMap2.put(KEY_ID, "third");
        list.add(hashMap2);

        HashMap<String, String> hashMap3 = new HashMap<>();
        hashMap3.put(KEY_NAME, "Paid Service");
        hashMap3.put(KEY_ID, "paid");
        list.add(hashMap3);

        HashMap<String, String> hashMap4 = new HashMap<>();
        hashMap4.put(KEY_NAME, "Accidental Repair");
        hashMap4.put(KEY_ID, "ar");
        list.add(hashMap4);

        HashMap<String, String> hashMap5 = new HashMap<>();
        hashMap5.put(KEY_NAME, "Running Repair");
        hashMap5.put(KEY_ID, "rr");
        list.add(hashMap5);

        HashMap<String, String> hashMap6 = new HashMap<>();
        hashMap6.put(KEY_NAME, "Insurance");
        hashMap6.put(KEY_ID, "Insurance");
        list.add(hashMap6);

        view.onSuccessServiceType(list);
    }


    @Override
    public void getLocation(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SERVICE_LOCATION, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessServiceLocation(response);
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
