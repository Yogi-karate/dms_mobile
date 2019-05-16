package com.dealermanagmentsystem.ui.base;

import android.app.Application;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.splunk.mint.Mint;
import com.squareup.otto.Bus;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends Application {

    private static Bus sEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        Mint.initAndStartSession(this, "20bbb23d");
        DMSPreference.startWith(getApplicationContext());
        initCalligraphy();
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/poppins_light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    public static Bus getEventBus() {
        if (sEventBus == null) {
            synchronized (Bus.class) {
                if (sEventBus == null) {
                    sEventBus = new Bus();
                }
            }
        }
        return sEventBus;
    }

    public static void setEventBus(Bus mockBus) {
        sEventBus = mockBus;
    }
}
