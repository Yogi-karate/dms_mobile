package com.dealermanagmentsystem.ui.saleorder.detail.price;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.socustomer.SOCustomerResponse;
import com.dealermanagmentsystem.data.model.soprice.SOPriceResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.ui.saleorder.detail.customer.ICustomerPresenter;
import com.dealermanagmentsystem.ui.saleorder.detail.customer.ICustomerView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_CUSTOMER;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_PRICE;

public class PricePresenter implements IPricePresenter {

    IPriceView view;

    public PricePresenter(IPriceView iPriceView) {
        view = iPriceView;
    }

    @Override
    public void getPrice(Activity activity, String saleOrderId) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SALE_ORDER_PRICE + saleOrderId, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    SOPriceResponse soPriceResponse = gson.fromJson(jsonObject.toString(), SOPriceResponse.class);
                    view.onSuccessPrice(soPriceResponse);
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
