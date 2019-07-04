package com.dealermanagmentsystem.ui.delivery;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.delivery.DeliveryResponse;
import com.dealermanagmentsystem.data.model.saleorder.SaleOrderResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.ui.saleorder.ISaleOrderPresenter;
import com.dealermanagmentsystem.ui.saleorder.ISaleOrderView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.DELIVERY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_SEARCH;

public class DeliveryPresenter implements IDeliveryPresenter {

    IDeliveryView view;

    public DeliveryPresenter(IDeliveryView iDeliveryView) {
        view = iDeliveryView;
    }


    @Override
    public void getDelivery(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(DELIVERY, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    DeliveryResponse deliveryResponse = gson.fromJson(jsonObject.toString(), DeliveryResponse.class);
                    view.onSuccessDelivery(deliveryResponse);
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
