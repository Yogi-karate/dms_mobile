package com.dealermanagmentsystem.ui.quotation;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.BAD_USERNAME_PASSWORD;
import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.CREATE_QUOTATION;
import static com.dealermanagmentsystem.constants.ConstantsUrl.PRICE_LIST;


public class QuotationCreatePresenter implements IQuotationCreatePresenter {

    IQuotationCreateView view;

    public QuotationCreatePresenter(IQuotationCreateView iQuotationCreateView) {
        view = iQuotationCreateView;
    }

    @Override
    public void getPriceList(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(PRICE_LIST, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onPriceListSuccess(response);
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
    public void createQuotation(Activity activity, int productId, int variantId, int colorId, int priceListId,
                                int strId, String strMobile, String strName, String strEmail,
                                int strTeamId, int strUserId) {

         if (variantId < 0) {
            view.onError("Please select a variant");
        } else if (colorId < 0) {
            view.onError("Please select a color");
        } else if (priceListId < 0) {
             view.onError("Please select a price list");
         } else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put("lead_id", strId);
                postDataParams.put("user_id", strUserId);
                postDataParams.put("team_id", strTeamId);
                postDataParams.put("partner_name",strName );
                postDataParams.put("partner_mobile", strMobile );
                postDataParams.put("partner_email", strEmail);
                postDataParams.put("product_id", productId);
                postDataParams.put("product_color", colorId);
                postDataParams.put("product_variant", variantId);
                postDataParams.put("pricelist", priceListId);

                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CREATE_QUOTATION, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        //Gson gson = new Gson();
                        //LoginResponse loginResponse = gson.fromJson(jsonObject.toString(), LoginResponse.class);
                        view.onQuotationSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(Result result) {
                    if (result.getStatusCode() == BAD_USERNAME_PASSWORD) {
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
