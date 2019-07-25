package com.dealermanagmentsystem.ui.login;

import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.login.LoginResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.BAD_USERNAME_PASSWORD;
import static com.dealermanagmentsystem.constants.Constants.MOBILE;
import static com.dealermanagmentsystem.constants.Constants.PASSWORD;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LOGIN;

public class LoginPresenter implements ILoginPresenter {

    ILoginView view;

    public LoginPresenter(ILoginView iLoginView) {
        view = iLoginView;
    }


    @Override
    public void doLogin(Activity activity, String mobileNo, String password) {

        if (TextUtils.isEmpty(mobileNo)) {
            view.onError("Mobile number field cannot be empty");
        } else if (mobileNo.length() != 10) {
            view.onError("Please enter a valid Mobile number");
        } else if (TextUtils.isEmpty(password)) {
            view.onError("Password field cannot be empty");
        } else {
            String json = "";
            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put(MOBILE, mobileNo);
                postDataParams.put(PASSWORD, password);
                json = postDataParams.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(LOGIN, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        Gson gson = new Gson();
                        LoginResponse loginResponse = gson.fromJson(jsonObject.toString(), LoginResponse.class);
                        view.onSuccessLogin(loginResponse);
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
