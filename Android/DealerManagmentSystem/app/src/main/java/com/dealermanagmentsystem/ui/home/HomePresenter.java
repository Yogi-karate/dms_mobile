package com.dealermanagmentsystem.ui.home;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.leadoverview.LeadOverviewResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LEAD_OVERVIEW;

public class HomePresenter implements IHomePresenter {

    IHomeView view;

    public HomePresenter(IHomeView iHomeView) {
        view = iHomeView;
    }

    @Override
    public void getLeadsOverview(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(LEAD_OVERVIEW, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONArray jsonObject = null;
                try {
                    jsonObject = new JSONArray(result.getResponse());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Gson gson = new Gson();
                // LeadOverviewResponse leadOverviewResponse = gson.fromJson(jsonObject.toString(), LeadOverviewResponse.class);
                // view.onSuccessLeadOverview(leadOverviewResponse);

               Gson gson = new Gson();
                String jsonOutput = result.getResponse();
                Type listType = new TypeToken<List<LeadOverviewResponse>>() {
                }.getType();
                List<LeadOverviewResponse> posts = gson.fromJson(jsonOutput, listType);


              /*  Type listType = new TypeToken<List<LeadOverviewResponse>>() {}.getType();
                List<LeadOverviewResponse> posts = new Gson().fromJson(jsonObject.toString(), listType);
*/
                view.onSuccessLeadOverview(posts);
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
