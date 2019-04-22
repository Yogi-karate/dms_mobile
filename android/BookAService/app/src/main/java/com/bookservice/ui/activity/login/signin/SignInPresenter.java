package com.bookservice.ui.activity.login.signin;

import android.app.Activity;
import android.text.TextUtils;

import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.bookservice.utils.ConnectionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.BAD_AUTHENTICATION;
import static com.bookservice.constants.Constants.MOBILE;
import static com.bookservice.constants.Constants.PIN;
import static com.bookservice.constants.Constants.POST;
import static com.bookservice.constants.ConstantsUrl.LOGIN;
import static com.bookservice.constants.ConstantsUrl.VERIFY;


public class SignInPresenter implements ISignInPresenter {

    ISignInView view;

    public SignInPresenter(ISignInView iSignInView) {
        view = iSignInView;
    }

    @Override
    public void verifyNumber(String strMobileNo, final Activity activity) {
        if (TextUtils.isEmpty(strMobileNo)) {
            view.onError("Mobile Number field cannot be empty");
        } else if (strMobileNo.length() > 10 || strMobileNo.length() < 10) {
            view.onError("Invalid Mobile Number, please enter 10 digit mobile number");
        } else {
            if (ConnectionUtils.isConnectedNetwork(activity)) {
                String json = "";
                JSONObject postDataParams = new JSONObject();
                try {
                    postDataParams.put(MOBILE, strMobileNo);
                    json = postDataParams.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(VERIFY, activity, json, POST, new IConnectionListener() {
                    @Override
                    public void onSuccess(Result result) {
                        view.onSuccess(result.getResponse());
                    }

                    @Override
                    public void onFail(Result result) {
                        if (result.getStatusCode() == BAD_AUTHENTICATION) {
                            view.onBadAuthenticate("Mobile Number not registered, please register");
                        } else {
                            view.onError("Something went wrong, Please try after sometime");
                        }
                    }
                });
                asyncTaskConnection.execute();

            } else {
                view.onError("No Active Network please connect to internet.");
            }
        }
    }

    @Override
    public void loginAttempt(String strMobileNo, String strPin, Activity activity) {
        if (TextUtils.isEmpty(strMobileNo)) {
            view.onError("Mobile Number field cannot be empty");
        } else if (strMobileNo.length() > 10 || strMobileNo.length() < 10) {
            view.onError("Invalid Mobile Number, please enter 10 digit mobile number");
        } else if (TextUtils.isEmpty(strPin)) {
            view.onError("Pin field cannot be empty");
        } else if (strPin.length() > 3) {
            view.onError("Pin should be more than 3 characters");
        } else {
            if (ConnectionUtils.isConnectedNetwork(activity)) {
                String json = "";
                JSONObject postDataParams = new JSONObject();
                try {
                    postDataParams.put(MOBILE, strMobileNo);
                    postDataParams.put(PIN, strPin);
                    json = postDataParams.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(LOGIN, activity, json, POST, new IConnectionListener() {
                    @Override
                    public void onSuccess(Result result) {
                        view.onSuccess(result.getResponse());
                    }

                    @Override
                    public void onFail(Result result) {
                        if (result.getStatusCode() == BAD_AUTHENTICATION) {
                            view.onError("Invalid username or password");
                        } else {
                            view.onError("Something went wrong, Please try after sometime");
                        }
                    }
                });
                asyncTaskConnection.execute();

            } else {
                view.onError("No Active Network please connect to internet.");
            }
        }
    }


}
