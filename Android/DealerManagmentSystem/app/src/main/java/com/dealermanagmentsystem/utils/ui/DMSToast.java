package com.dealermanagmentsystem.utils.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


public class DMSToast {

    static String TAG = "DMSToast";

    public static void showLong(Context context, @Nullable CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, @StringRes int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, @Nullable CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, @StringRes int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    /**
     * Default
     */
    private static void show(Context context, @Nullable CharSequence text, int duration) {
        if (context != null && !TextUtils.isEmpty(text)) {
            try {
                Toast.makeText(context.getApplicationContext(), text, duration).show();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }
}
