package com.bookservice.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bookservice.R;

import com.bookservice.ui.activity.home.HomeActivity;
import com.bookservice.ui.activity.login.signin.ISignInView;
import com.bookservice.ui.activity.login.signin.SignInPresenter;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BsBaseActivity implements ISignInView {

    @BindView(R.id.et_mobile_no)
    EditText etMobileNo;
    @BindView(R.id.et_pin)
    EditText etPin;
    @BindView(R.id.ll_pin)
    LinearLayout llPin;

    SignInPresenter signInPresenter;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        showFullScreen();
        activity = SignInActivity.this;
        signInPresenter = new SignInPresenter(this);
    }

    @OnClick(R.id.btn_sign_in) //ButterKnife uses.
    public void launchHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        /*String strMobileNo = etMobileNo.getText().toString();
        if (llPin.getVisibility() == View.VISIBLE) {
            String strPin = etPin.getText().toString();
            signInPresenter.loginAttempt(strMobileNo, strPin, activity);
        } else {
            signInPresenter.verifyNumber(strMobileNo, activity);
        }*/
    }

    @Override
    public void onSuccess(String message) {
        llPin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }

    @Override
    public void onBadAuthenticate(String message) {
        BsToast.showLong(activity, message);
        Intent intent = new Intent(this, OtpActivity.class);
        startActivity(intent);
    }
}
