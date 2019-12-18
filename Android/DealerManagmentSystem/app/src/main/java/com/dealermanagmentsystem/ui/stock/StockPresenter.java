package com.dealermanagmentsystem.ui.stock;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.saleorder.SaleOrderResponse;
import com.dealermanagmentsystem.data.model.stock.StockResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.ui.saleorder.ISaleOrderPresenter;
import com.dealermanagmentsystem.ui.saleorder.ISaleOrderView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.STOCK_LIST;

public class StockPresenter implements IStockPresenter {

    IStockView view;

    public StockPresenter(IStockView iStockView) {
        view = iStockView;
    }

    @Override
    public void getStock(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(STOCK_LIST, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    StockResponse stockResponse = gson.fromJson(jsonObject.toString(), StockResponse.class);
                    view.onSuccessStock(stockResponse);
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
