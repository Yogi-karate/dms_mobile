package com.dealermanagmentsystem.ui.insurance.create;

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

import static com.dealermanagmentsystem.constants.Constants.ALTERNATE_NO;
import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.BOOKING_TYPE;
import static com.dealermanagmentsystem.constants.Constants.CURRENT_DUE_DATE;
import static com.dealermanagmentsystem.constants.Constants.CURRENT_INSURANCE_COMPANY;
import static com.dealermanagmentsystem.constants.Constants.CURRENT_NCB;
import static com.dealermanagmentsystem.constants.Constants.DISCOUNT;
import static com.dealermanagmentsystem.constants.Constants.DOP;
import static com.dealermanagmentsystem.constants.Constants.FINAL_DIP_COMP;
import static com.dealermanagmentsystem.constants.Constants.FINAL_PREMIUM;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.IDV;
import static com.dealermanagmentsystem.constants.Constants.KEY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_NAME;
import static com.dealermanagmentsystem.constants.Constants.LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.LOCATION_ID;
import static com.dealermanagmentsystem.constants.Constants.PICK_UP_ADDRESS;
import static com.dealermanagmentsystem.constants.Constants.POLICY_NO;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.Constants.PREVIOUS_DIP_COMP;
import static com.dealermanagmentsystem.constants.Constants.PREVIOUS_DUE_DATE;
import static com.dealermanagmentsystem.constants.Constants.PREVIOUS_FINAL_PREMIUM;
import static com.dealermanagmentsystem.constants.Constants.PREVIOUS_IDV;
import static com.dealermanagmentsystem.constants.Constants.PREVIOUS_INSURANCE_COMPANY;
import static com.dealermanagmentsystem.constants.Constants.PREVIOUS_NCB;
import static com.dealermanagmentsystem.constants.Constants.REMARKS;
import static com.dealermanagmentsystem.constants.ConstantsUrl.CREATE_INSURANCE_BOOKING;
import static com.dealermanagmentsystem.constants.ConstantsUrl.CREATE_SERVICE_BOOKING;
import static com.dealermanagmentsystem.constants.ConstantsUrl.INSURANCE_BANK_LIST;

public class InsuranceCreateBookingPresenter implements IInsuranceCreateBookingPresenter {

    IInsuranceCreateBookingView view;

    public InsuranceCreateBookingPresenter(IInsuranceCreateBookingView iInsuranceCreateBookingView) {
        view = iInsuranceCreateBookingView;
    }


    @Override
    public void createInsuranceBooking(Activity activity, String leadId, int previousCompanyId,
                                       String strPreviousNilDip, String strPreviousIDV, String strPreviousNCB,
                                       String strDiscount, String strPreviousDueDate, String strPreviousFinalPremium,
                                       String strPreviousPolicyNo, int currentCompanyId, String strCurrentNilDip,
                                       String strBookingTypeId, String strCurrentIDV, String strCurrentNCB,
                                       String strAlternateNumber, String strCurrentDueDate,
                                       String strCurrentFinalPremium, String strPickUpDate, String strPickUpAddress) {

        if (TextUtils.isEmpty(strPickUpDate)) {
            view.onError("please select a appointment date and time");
        } else if (TextUtils.isEmpty(strPickUpAddress) && strBookingTypeId.equalsIgnoreCase("pickup")) {
            view.onError("please enter a pick up address");
        } else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(DOP, strPickUpDate);
                if (!TextUtils.isEmpty(strPickUpAddress))
                    postDataParams.put(PICK_UP_ADDRESS, strPickUpAddress);
                if (!TextUtils.isEmpty(strCurrentIDV)) postDataParams.put(IDV, strCurrentIDV);
                if (!TextUtils.isEmpty(strCurrentNCB))
                    postDataParams.put(CURRENT_NCB, strCurrentNCB);
                if (!TextUtils.isEmpty(strPreviousPolicyNo))
                    postDataParams.put(POLICY_NO, strPreviousPolicyNo);
                if (!TextUtils.isEmpty(strPreviousIDV))
                    postDataParams.put(PREVIOUS_IDV, strPreviousIDV);
                if (!TextUtils.isEmpty(strPreviousFinalPremium))
                    postDataParams.put(PREVIOUS_FINAL_PREMIUM, strPreviousFinalPremium);
                if (!TextUtils.isEmpty(strPreviousNCB))
                    postDataParams.put(PREVIOUS_NCB, strPreviousNCB);
                if (!TextUtils.isEmpty(strPreviousDueDate))
                    postDataParams.put(PREVIOUS_DUE_DATE, strPreviousDueDate);
                if (!TextUtils.isEmpty(strDiscount)) postDataParams.put(DISCOUNT, strDiscount);
                if (!TextUtils.isEmpty(strCurrentFinalPremium))
                    postDataParams.put(FINAL_PREMIUM, strCurrentFinalPremium);
                if (!TextUtils.isEmpty(strCurrentDueDate))
                    postDataParams.put(CURRENT_DUE_DATE, strCurrentDueDate);
                if (!TextUtils.isEmpty(strAlternateNumber))
                    postDataParams.put(ALTERNATE_NO, strAlternateNumber);

                postDataParams.put(BOOKING_TYPE, strBookingTypeId);
                postDataParams.put(PREVIOUS_INSURANCE_COMPANY, previousCompanyId);
                postDataParams.put(PREVIOUS_DIP_COMP, strPreviousNilDip);
                postDataParams.put(FINAL_DIP_COMP, strCurrentNilDip);
                postDataParams.put(CURRENT_INSURANCE_COMPANY, currentCompanyId);
                postDataParams.put(LEAD_ID, leadId);
                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CREATE_INSURANCE_BOOKING, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        Gson gson = new Gson();
                        CommonResponse response = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                        view.onSuccessInsuranceCreateBooking(response);
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

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put(KEY_NAME, "Online Payment");
        hashMap2.put(KEY_ID, "online_payment");
        list.add(hashMap2);

        view.onSuccessBookingType(list);
    }

    @Override
    public void getNilDip() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(KEY_NAME, "Comprehensive");
        hashMap.put(KEY_ID, "comprehensive");
        list.add(hashMap);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put(KEY_NAME, "NIL-DIP");
        hashMap1.put(KEY_ID, "nil-dip");
        list.add(hashMap1);
        view.onSuccessNilDipType(list);
    }

    @Override
    public void getBanks(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(INSURANCE_BANK_LIST, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessBank(response);
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
