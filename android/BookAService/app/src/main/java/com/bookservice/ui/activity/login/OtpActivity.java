package com.bookservice.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;

import com.bookservice.R;
import com.bookservice.ui.base.BsBaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpActivity extends BsBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        showFullScreen();
    }

    @OnClick(R.id.btn_submit_otp) //ButterKnife uses.
    public void openSignUp() {
        Intent intent = new Intent(OtpActivity.this, PinConfirmationActivity.class);
        startActivity(intent);
    }
}
