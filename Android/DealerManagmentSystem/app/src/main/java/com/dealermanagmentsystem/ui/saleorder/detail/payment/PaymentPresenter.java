package com.dealermanagmentsystem.ui.saleorder.detail.payment;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.sopayment.SOPaymentResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_PAYMENTS;

public class PaymentPresenter implements IPaymentPresenter {

    IPaymentView view;

    public PaymentPresenter(IPaymentView iPaymentView) {
        view = iPaymentView;
    }

    @Override
    public void getPaymentList(Activity activity, String saleOrderId) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SALE_ORDER_PAYMENTS + saleOrderId, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    SOPaymentResponse soPaymentResponse = gson.fromJson(jsonObject.toString(), SOPaymentResponse.class);
                    view.onSuccessPaymentList(soPaymentResponse);
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
