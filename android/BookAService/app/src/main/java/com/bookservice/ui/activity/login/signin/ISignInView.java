package com.bookservice.ui.activity.login.signin;

import com.bookservice.network.Result;

public interface ISignInView {

    public void onSuccess(String result);

    public void onError(String result);

    public void onBadAuthenticate(String result);
}
