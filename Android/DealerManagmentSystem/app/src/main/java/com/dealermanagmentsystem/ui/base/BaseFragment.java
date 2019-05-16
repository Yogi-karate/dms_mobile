package com.dealermanagmentsystem.ui.base;


import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.getEventBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.getEventBus().unregister(this);

    }


}
