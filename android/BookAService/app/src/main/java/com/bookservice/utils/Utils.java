package com.bookservice.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
    public static final String DATE_FORMATTER_FOR_SYNC_DATA = "yyyy-MM-dd";
    public static final String DATE_FORMATTER_WITH_TIME = "yyyy-MM-dd HH:mm";

    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }

    public static void share(Activity activity) {
        String shareBody = "Here is the share content body";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share Offers from Book a Service"));
    }

    public static void hideIME(final Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    public static long getTimeInMilliSecs() {
        return System.currentTimeMillis();
    }

    public static String getTimeInUTC() {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        calendar.setTime(myDate);
        calendar.add(Calendar.MINUTE, -2);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat(DATE_FORMATTER_FOR_SYNC_DATA);
        outputFmt.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String dateAsString = outputFmt.format(time);

        return dateAsString;
    }

    public static String getTime() {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        calendar.setTime(myDate);
        calendar.add(Calendar.MINUTE, -1);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat(DATE_FORMATTER_WITH_TIME);
        outputFmt.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String dateAsString = outputFmt.format(time);

        return dateAsString;
    }
}
