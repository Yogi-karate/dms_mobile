package com.dealermanagmentsystem.ui.enquiry.vehicledetail;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.Constants.PRODUCT_ID;
import static com.dealermanagmentsystem.constants.Constants.VARIANT_ID;
import static com.dealermanagmentsystem.constants.ConstantsUrl.COLORS;
import static com.dealermanagmentsystem.constants.ConstantsUrl.PRODUCT;
import static com.dealermanagmentsystem.constants.ConstantsUrl.VARIANTS;

public class VehiclePresenter implements IVehiclePresenter {

    IVehicleView view;

    public VehiclePresenter(IVehicleView iVehicleView) {
        view = iVehicleView;
    }

    @Override
    public void getProduct(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(PRODUCT, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessProducts(response);
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
    public void getVariants(Activity activity, int id) {

        String json = "";
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(PRODUCT_ID, id);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(VARIANTS, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessVariants(response);
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
    public void getColors(Activity activity, int strProductId, int strVariantId) {
        String json = "";
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(PRODUCT_ID, strProductId);
            postDataParams.put(VARIANT_ID, strVariantId);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(COLORS, activity, json, POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessColors(response);
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
