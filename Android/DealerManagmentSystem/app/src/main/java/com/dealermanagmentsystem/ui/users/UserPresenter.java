package com.dealermanagmentsystem.ui.users;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.loadusers.LoadUserResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LOAD_USERS;

public class UserPresenter implements IUserPresenter {

    IUserView view;

    public UserPresenter(IUserView iUserView) {
        view = iUserView;
    }

    @Override
    public void getUsers(Activity activity, String str) {
        String url = "";
        if (str.equalsIgnoreCase("refresh")) {
            url = LOAD_USERS + "?action=refresh";
        } else {
            url = LOAD_USERS;
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(url, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    LoadUserResponse loadUserResponse = gson.fromJson(jsonObject.toString(), LoadUserResponse.class);
                    view.onSuccessUserList(loadUserResponse);
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
