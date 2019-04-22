package com.bookservice.network;


import android.app.Activity;
import android.os.AsyncTask;

import com.bookservice.utils.ProgressDialogUtil;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import static com.bookservice.constants.Constants.OK;
import static com.bookservice.constants.Constants.POST;

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
        if (mMethodType.equalsIgnoreCase(POST)) {
            return WsConnection.doPostConnection(mUrl, mJson);
        } else {
            return WsConnection.doGetConnection(mUrl);
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        flipProgressDialog.dismiss();
        if (result.getStatusCode() == OK) {
            mConnectionListener.onSuccess(result);
        } else {
            mConnectionListener.onFail(result);
        }
    }
}
