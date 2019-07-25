package com.dealermanagmentsystem.network;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.dealermanagmentsystem.preference.DMSPreference;
import com.dealermanagmentsystem.ui.login.LoginActivity;
import com.dealermanagmentsystem.utils.ConnectionUtils;
import com.dealermanagmentsystem.utils.progress.ProgressDialogUtil;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import static com.dealermanagmentsystem.constants.Constants.BAD_AUTHENTICATION;
import static com.dealermanagmentsystem.constants.Constants.EXCEPTION_CODE;
import static com.dealermanagmentsystem.constants.Constants.KEY_FCM_TOKEN_SET;
import static com.dealermanagmentsystem.constants.Constants.KEY_TOKEN;
import static com.dealermanagmentsystem.constants.Constants.KEY_USERNAME;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_EMAIL_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_IMAGE;
import static com.dealermanagmentsystem.constants.Constants.NO_INTERNET;
import static com.dealermanagmentsystem.constants.Constants.NO_INTERNET_CONNECTION;
import static com.dealermanagmentsystem.constants.Constants.OK;
import static com.dealermanagmentsystem.constants.Constants.POST;


public class AsyncTaskConnection extends AsyncTask<String, Void, Result> {

    private IConnectionListener mConnectionListener;
    private Activity mActivity;
    private String mUrl;
    private FlipProgressDialog flipProgressDialog;
    private String mJson;
    private String mMethodType;

    public AsyncTaskConnection(String url, Activity activity, String json, String methodType,
                               IConnectionListener connectionListener) {
        mActivity = activity;
        mConnectionListener = connectionListener;
        mUrl = url;
        mJson = json;
        mMethodType = methodType;

    }

    public AsyncTaskConnection(String url, Activity activity, String methodType,
                               IConnectionListener connectionListener) {
        mActivity = activity;
        mConnectionListener = connectionListener;
        mUrl = url;
        mMethodType = methodType;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        flipProgressDialog = ProgressDialogUtil.showProgressDialog(mActivity);
    }

    @Override
    protected Result doInBackground(String... strings) {
        if (ConnectionUtils.isConnectedNetwork(mActivity)) {
            if (mMethodType.equalsIgnoreCase(POST)) {
                return WsConnection.doPostConnection(mUrl, mJson);
            } else {
                return WsConnection.doGetConnection(mUrl);
            }
        } else {
            Result result = new Result();
            result.setResponse(NO_INTERNET_CONNECTION);
            result.setStatusCode(NO_INTERNET);
            return result;
        }

    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        flipProgressDialog.dismiss();
        if (result.getStatusCode() == OK) {
            mConnectionListener.onSuccess(result);
        } else if (result.getStatusCode() == NO_INTERNET) {
            mConnectionListener.onNetworkFail(result.getResponse());
        } else if (result.getStatusCode() == BAD_AUTHENTICATION) {
            DMSPreference.setString(KEY_TOKEN, "");
            DMSPreference.setString(KEY_USERNAME, "");
            DMSPreference.setString(KEY_USER_EMAIL_ID, "");
            DMSPreference.setString(KEY_USER_IMAGE, "");
            //  DMSPreference.setString(KEY_FCM_TOKEN, "");
            DMSPreference.setBoolean(KEY_FCM_TOKEN_SET, false);

            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mActivity.startActivity(intent);
            DMSToast.showLong(mActivity,"Session expired, please login..");
        } else {
            mConnectionListener.onFail(result);
        }


    }
}
