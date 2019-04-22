package com.bookservice.ui.activity.login.signin;

import android.app.Activity;

public interface ISignInPresenter {

    public void verifyNumber(String strMobileNo, Activity activity);

    public void loginAttempt(String strMobileNo, String strPin, Activity activity);
}
