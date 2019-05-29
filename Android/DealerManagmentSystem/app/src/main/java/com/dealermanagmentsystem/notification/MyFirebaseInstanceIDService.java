package com.dealermanagmentsystem.notification;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.FCM_TOKEN_PARAM;
import static com.dealermanagmentsystem.constants.Constants.KEY_FCM_TOKEN;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SEND_FCM_TOKEN;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

       // if (TextUtils.isEmpty(DMSPreference.getString(KEY_FCM_TOKEN))) {
            storeRegIdInPrefAndSend(refreshedToken);
            // Notify UI that registration has completed, so the progress indicator can be hidden.
          /*  Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
            registrationComplete.putExtra("token", refreshedToken);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);*/
      //  }
    }


    private void storeRegIdInPrefAndSend(String token) {
        Log.d("token", token);
        DMSPreference.setString(KEY_FCM_TOKEN, token);
    }
}

