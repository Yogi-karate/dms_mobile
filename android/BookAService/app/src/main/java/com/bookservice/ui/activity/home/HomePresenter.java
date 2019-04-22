package com.bookservice.ui.activity.home;

import android.app.Activity;

import com.bookservice.data.model.category.CategoryResponse;
import com.bookservice.data.model.offers.GetOffersResponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.bookservice.preference.BsPreference;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import static com.bookservice.constants.Constants.GET;
import static com.bookservice.constants.ConstantsUrl.CATEGORIES;
import static com.bookservice.constants.ConstantsUrl.OFFERS;

public class HomePresenter implements IHomePresenter {

    IHomeView view;

    public HomePresenter(IHomeView iHomeView) {
        view = iHomeView;
    }

    @Override
    public void getOffers(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(OFFERS, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    GetOffersResponse getOffersResponse = gson.fromJson(jsonObject.toString(), GetOffersResponse.class);
                    view.onSuccessOffers(getOffersResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void getCategories(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CATEGORIES, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    CategoryResponse categoryResponse = gson.fromJson(jsonObject.toString(), CategoryResponse.class);
                    view.onSuccessCategory(categoryResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }
        });
        asyncTaskConnection.execute();
    }

}
