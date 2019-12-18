package com.dealermanagmentsystem.ui.saleorder.detail.customer;

import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.saleorder.SaleOrderResponse;
import com.dealermanagmentsystem.data.model.socustomer.SOCustomerResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.ui.saleorder.ISaleOrderPresenter;
import com.dealermanagmentsystem.ui.saleorder.ISaleOrderView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_CONFIRM_QUOTATION;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_CUSTOMER;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_SEARCH;

public class CustomerPresenter implements ICustomerPresenter {

    ICustomerView view;

    public CustomerPresenter(ICustomerView iCustomerView) {
        view = iCustomerView;
    }

    @Override
    public void getCustomer(Activity activity, String saleOrderId) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SALE_ORDER_CUSTOMER + saleOrderId, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    SOCustomerResponse soCustomerResponse = gson.fromJson(jsonObject.toString(), SOCustomerResponse.class);
                    view.onSuccessCustomer(soCustomerResponse);
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
    public void confirmQuotation(Activity activity, String saleOrderId) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SALE_ORDER_CONFIRM_QUOTATION + saleOrderId, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    final String message = jsonObject.getString("message");
                    view.onSuccessConfirmQuotation(message);
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
