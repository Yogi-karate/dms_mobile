package com.bookservice.ui.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bookservice.R;
import com.bookservice.ui.activity.login.SignInActivity;
import com.bookservice.ui.base.BsBaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends BsBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        showFullScreen();

    }

    @OnClick(R.id.get_started) //ButterKnife uses.
    public void getStarted() {
        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }


}
