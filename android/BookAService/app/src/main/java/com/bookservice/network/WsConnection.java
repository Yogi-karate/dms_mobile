package com.bookservice.network;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.SSLEngineResult;

import static com.bookservice.constants.Constants.BAD_AUTHENTICATION;
import static com.bookservice.constants.Constants.EXCEPTION_CODE;
import static com.bookservice.constants.Constants.OK;
import static com.bookservice.constants.Constants.TIMEOUT;


public class WsConnection {


    public static Result doGetConnection(String strUrl) {
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
        } catch (Exception e) {
            result.setResponse("");
            result.setStatusCode(EXCEPTION_CODE);
        }

        return result;

    }

    public static Result doPostConnection(String strUrl, String strJson) {
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
            //urlConnection.setRequestProperty("token", BsPreference.getString(KEY_TOKEN));

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
        } catch (Exception e) {
            result.setStatusCode(EXCEPTION_CODE);
            result.setResponse("");
        }
        return result;

    }
}
