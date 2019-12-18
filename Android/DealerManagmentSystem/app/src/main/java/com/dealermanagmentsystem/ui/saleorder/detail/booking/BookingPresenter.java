package com.dealermanagmentsystem.ui.saleorder.detail.booking;

import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.sobooking.SOBookingResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.KEY_DELIVERY_DATE;
import static com.dealermanagmentsystem.constants.Constants.KEY_FINANCE_PAYMENT_DATE;
import static com.dealermanagmentsystem.constants.Constants.KEY_FINANCE_PMT;
import static com.dealermanagmentsystem.constants.Constants.KEY_FINANCE_TYPE_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_FINANCIER_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_MARGIN_PAYMENT_DATE;
import static com.dealermanagmentsystem.constants.Constants.KEY_MARGIN_PMT;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.EDIT_SALE_ORDER;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_BOOKING;

public class BookingPresenter implements IBookingPresenter {

    IBookingView view;

    public BookingPresenter(IBookingView iBookingView) {
        view = iBookingView;
    }

    @Override
    public void getBookingDetails(Activity activity, String saleOrderId) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SALE_ORDER_BOOKING + saleOrderId, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    SOBookingResponse bookingResponse = gson.fromJson(jsonObject.toString(), SOBookingResponse.class);
                    view.onSuccessBookingDetails(bookingResponse);
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
    public void updateBookingDetails(Activity activity, String saleOrderId,
                                     String strFinanceTypeId, String strFinancierId,
                                     String sFinanceAmount, String sFinancePaymentDate,
                                     String sMarginMoneyAmount, String sMarginMoneyDate, String sDeliveryDate) {

        if (TextUtils.isEmpty(strFinancierId)) {
            view.onError("Please select a financier");
        } else if (TextUtils.isEmpty(sFinanceAmount)) {
            view.onError("Please enter finance amount");
        } else if (TextUtils.isEmpty(sFinancePaymentDate)) {
            view.onError("Please enter finance payment date");
        } else if (TextUtils.isEmpty(sMarginMoneyAmount)) {
            view.onError("Please enter margin money amount");
        } else if (TextUtils.isEmpty(sMarginMoneyDate)) {
            view.onError("Please enter margin money date");
        } else if (TextUtils.isEmpty(sDeliveryDate)) {
            view.onError("Please enter delivery date");
        } else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(KEY_FINANCE_TYPE_ID, strFinanceTypeId);
                postDataParams.put(KEY_FINANCIER_ID, strFinancierId);
                postDataParams.put(KEY_FINANCE_PMT, sFinanceAmount);
                postDataParams.put(KEY_FINANCE_PAYMENT_DATE, sFinancePaymentDate);
                postDataParams.put(KEY_DELIVERY_DATE, sDeliveryDate);
                postDataParams.put(KEY_MARGIN_PMT, sMarginMoneyAmount);
                postDataParams.put(KEY_MARGIN_PAYMENT_DATE, sMarginMoneyDate);

                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(EDIT_SALE_ORDER + saleOrderId, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        Gson gson = new Gson();
                        CommonResponse response = gson.fromJson(jsonObject.toString(), CommonResponse.class);
                        view.onSuccessUpdateBookingDetails(response);
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
}
