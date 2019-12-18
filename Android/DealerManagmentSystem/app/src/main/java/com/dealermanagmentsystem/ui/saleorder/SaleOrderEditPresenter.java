package com.dealermanagmentsystem.ui.saleorder;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_DELIVERY_DATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FINANCE_TYPE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FINANCIER_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_PAYMENT_DATE;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.CREATE_ACTIVITY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.EDIT_SALE_ORDER;

public class SaleOrderEditPresenter implements ISaleOrderEditPresenter {

    ISaleOrderEditView view;

    public SaleOrderEditPresenter(ISaleOrderEditView iSaleOrderEditView) {
        view = iSaleOrderEditView;
    }


    @Override
    public void postSaleOrderEdit(Activity activity, String sPaymentDate,
                                  String sDeliveryDate, String financeType, String financier, String id) {
        String json = "";
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(EXTRA_FINANCE_TYPE, financeType);
            postDataParams.put(EXTRA_FINANCIER_NAME, financier);
            postDataParams.put(EXTRA_PAYMENT_DATE, sPaymentDate);
            postDataParams.put(EXTRA_DELIVERY_DATE, sDeliveryDate);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(EDIT_SALE_ORDER + id, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    CommonResponse response = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                    view.onSuccessSaleOrderEdit(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                if (result.getStatusCode() == BAD_AUTHENTICATION) {
                    view.onErrorMessage("Wrong username or password");
                } else {
                    view.onErrorMessage("Something went wrong, Please try after sometime");
                }
            }

            @Override
            public void onNetworkFail(String message) {
                view.onErrorMessage(message);
            }
        });
        asyncTaskConnection.execute();

    }
}
