package com.bookservice.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.ui.activity.home.HomeActivity;
import com.bookservice.ui.base.BsBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinConfirmationActivity extends BsBaseActivity {

    @BindView(R.id.txt_terms_conditions)
    TextView txtTermsConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_confirmation);
        ButterKnife.bind(this);
        showFullScreen();
        txtTermsConditions.setText(Html.fromHtml("<font color=#ffffff>By creating an account, your agree to our</font> <font color=#D97D54>Terms of service</font>  <font color=#ffffff> and</font> <font color=#D97D54>Privacy Policy</font>"));
    }

    @OnClick(R.id.btn_pin_submit) //ButterKnife uses.
    public void openSignIn() {
        Intent intent = new Intent(PinConfirmationActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
