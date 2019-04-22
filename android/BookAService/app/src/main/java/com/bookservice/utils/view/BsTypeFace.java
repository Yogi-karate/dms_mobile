package com.bookservice.utils.view;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by nikhil.v on 6/16/2017.
 */

public class BsTypeFace {

    private static Typeface appTypeface;

    public static Typeface getTypeface(Context context) {
        if (appTypeface == null) {
            appTypeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/avenirLTStd-Light.otf");
        }
        return appTypeface;
    }
}
