package com.dealermanagmentsystem.network;


import android.app.Activity;
import android.os.AsyncTask;

import com.dealermanagmentsystem.utils.ConnectionUtils;
import com.dealermanagmentsystem.utils.progress.ProgressDialogUtil;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import static com.dealermanagmentsystem.constants.Constants.EXCEPTION_CODE;
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
        } else {
            mConnectionListener.onFail(result);
        }
    }
}
