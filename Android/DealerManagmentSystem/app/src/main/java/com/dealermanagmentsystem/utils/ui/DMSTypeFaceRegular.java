package com.dealermanagmentsystem.utils.ui;

import android.content.Context;
import android.graphics.Typeface;

public class DMSTypeFaceRegular {
    private static Typeface appTypeface;

    public static Typeface getTypeface(Context context) {
        if (appTypeface == null) {
            appTypeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/poppins_regular.otf");
        }
        return appTypeface;
    }
}

