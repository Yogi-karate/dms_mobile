package com.dealermanagmentsystem.ui.login;

import android.app.Activity;

public interface ILoginPresenter {

    public void doLogin(Activity activity, String mobileNo, String password);

    public void getEndPoint(Activity activity);
}
