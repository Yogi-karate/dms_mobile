package com.bookservice.utils.view;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by nikhil.v on 7/13/2017.
 */

public class BsTypeFaceBold {

    private static Typeface appTypeface;

    public static Typeface getTypeface(Context context) {
        if (appTypeface == null) {
            appTypeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/avenirltstd-bold.otf");
        }
        return appTypeface;
    }
}
