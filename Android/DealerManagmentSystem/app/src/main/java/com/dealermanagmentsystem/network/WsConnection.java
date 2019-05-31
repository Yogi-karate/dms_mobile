package com.dealermanagmentsystem.network;


import com.dealermanagmentsystem.preference.DMSPreference;
import com.splunk.mint.Mint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static com.dealermanagmentsystem.constants.Constants.EXCEPTION_CODE;
import static com.dealermanagmentsystem.constants.Constants.KEY_TOKEN;
import static com.dealermanagmentsystem.constants.Constants.OK;
import static com.dealermanagmentsystem.constants.Constants.TIMEOUT;


public class WsConnection {

    public static Result doGetConnection(String strUrl) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        String inputLine = null;
        HttpURLConnection urlConnection = null;
        int responseCode = 0;
        Result result = new Result();
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(TIMEOUT);
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setRequestProperty("Authorization", "JWT " + DMSPreference.getString(KEY_TOKEN));
            urlConnection.connect();

            responseCode = urlConnection.getResponseCode();
            if (responseCode == OK) {
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(streamReader);
                StringBuilder sb = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                br.close();
                streamReader.close();
                result.setResponse(sb.toString());
                result.setStatusCode(responseCode);
            } else {
                result.setResponse("");
                result.setStatusCode(responseCode);
            }
            long endTime = Calendar.getInstance().getTimeInMillis();
            Mint.logNetworkEvent(strUrl, "", startTime, endTime, responseCode,
                    1, 1, "", null);
        } catch (Exception e) {
            result.setResponse("");
            result.setStatusCode(EXCEPTION_CODE);
        }

        return result;

    }

    public static Result doPostConnection(String strUrl, String strJson) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        String inputLine = null;
        HttpURLConnection urlConnection = null;
        int responseCode = 0;
        Result result = new Result();

        try {

            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(TIMEOUT);
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "JWT " + DMSPreference.getString(KEY_TOKEN));

            urlConnection.connect();
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(strJson);
            out.close();

            responseCode = urlConnection.getResponseCode();
            if (responseCode == OK) {
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(streamReader);
                StringBuilder sb = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                br.close();
                streamReader.close();
                result.setStatusCode(responseCode);
                result.setResponse(sb.toString());
            } else {
                result.setStatusCode(responseCode);
                result.setResponse("");
            }
            long endTime = Calendar.getInstance().getTimeInMillis();
            Mint.logNetworkEvent(strUrl, "", startTime, endTime, responseCode,
                    1, 1, "", null);
        } catch (Exception e) {
            result.setStatusCode(EXCEPTION_CODE);
            result.setResponse("");
        }
        return result;

    }
}
