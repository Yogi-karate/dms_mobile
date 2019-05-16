package com.dealermanagmentsystem.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Utils {

    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && email.contains("@"));
    }
}
