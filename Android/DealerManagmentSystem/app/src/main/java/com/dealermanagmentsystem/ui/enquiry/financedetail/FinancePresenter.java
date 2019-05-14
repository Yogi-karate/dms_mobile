package com.dealermanagmentsystem.ui.enquiry.financedetail;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.KEY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_NAME;
import static com.dealermanagmentsystem.constants.ConstantsUrl.FINANCIER;

public class FinancePresenter implements IFinancePresenter {

    IFinanceView view;

    public FinancePresenter(IFinanceView iFinanceView) {
        view = iFinanceView;
    }

    @Override
    public void getFinancier(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(FINANCIER, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response response = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessFinancier(response);
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
    public void getFinanceType(Activity activity) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(KEY_NAME,"In-House");
        hashMap.put(KEY_ID,"in");
        list.add(hashMap);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put(KEY_NAME,"Out-House");
        hashMap1.put(KEY_ID,"out");
        list.add(hashMap1);

        view.onSuccessFinanceType(list);
    }
}
