package com.dealermanagmentsystem.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.login.LoginResponse;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.home.HomeActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.KEY_TOKEN;
import static com.dealermanagmentsystem.constants.Constants.KEY_USERNAME;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_EMAIL_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_IMAGE;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    Activity activity;
    LoginPresenter loginPresenter;
    @BindView(R.id.et_mobile_no)
    EditText etMobileNo;
    @BindView(R.id.et_pin)
    EditText etPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = LoginActivity.this;
        ButterKnife.bind(this);
        showFullScreen();
        loginPresenter = new LoginPresenter(
                this);

    }

    @OnClick(R.id.btn_sign_in) //ButterKnife uses.
    public void launchHomePage() {
       /* Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);*/
        final String strMobileNo = etMobileNo.getText().toString();
        final String strPin = etPin.getText().toString();
        loginPresenter.doLogin(activity, strMobileNo, strPin);
    }

    @Override
    public void onSuccessLogin(LoginResponse response) {
        if (response.getAuth()) {
            DMSPreference.setString(KEY_TOKEN, response.getToken());
            DMSPreference.setString(KEY_USERNAME, response.getName());
            DMSPreference.setString(KEY_USER_EMAIL_ID, response.getEmail());
            DMSPreference.setString(KEY_USER_IMAGE, response.getImage());

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            DMSToast.showLong(activity, "Logged in successfully");
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}

