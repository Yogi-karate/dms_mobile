package com.dealermanagmentsystem.utils.ui;

import android.content.Context;
import android.graphics.Typeface;


public class DMSTypeFace {

    private static Typeface appTypeface;

    public static Typeface getTypeface(Context context) {
        if (appTypeface == null) {
            appTypeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/poppins_light.otf");
        }
        return appTypeface;
    }
}
