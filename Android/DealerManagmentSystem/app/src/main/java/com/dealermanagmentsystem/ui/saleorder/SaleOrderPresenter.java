package com.dealermanagmentsystem.ui.saleorder;

import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.saleorder.SaleOrderResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SALE_ORDER_SEARCH;

public class SaleOrderPresenter implements ISaleOrderPresenter {

    ISaleOrderView view;

    public SaleOrderPresenter(ISaleOrderView iSaleOrderView) {
        view = iSaleOrderView;
    }

    @Override
    public void getSaleOrder(Activity activity, String strSaleType, String leadId) {
        String url;
       /* if ("to invoice".equalsIgnoreCase(strSaleType)) {
            url = SALE_ORDER_SEARCH + "invoice_status=" + strSaleType;
        } else {*/

        //}

        if (TextUtils.isEmpty(leadId)) {
            url = SALE_ORDER_SEARCH + "state=" + strSaleType;
        } else {
            url = SALE_ORDER_SEARCH + "leadId=" + leadId;
        }
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(url, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    SaleOrderResponse saleOrderResponse = gson.fromJson(jsonObject.toString(), SaleOrderResponse.class);
                    view.onSuccessSaleOrder(saleOrderResponse);
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
