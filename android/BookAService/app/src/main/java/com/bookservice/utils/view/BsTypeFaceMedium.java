package com.bookservice.utils.view;

import android.content.Context;
import android.graphics.Typeface;

public class BsTypeFaceMedium {
    private static Typeface appTypeface;

    public static Typeface getTypeface(Context context) {
        if (appTypeface == null) {
            appTypeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/avenirLTStd-Medium.otf");
        }
        return appTypeface;
    }
}

