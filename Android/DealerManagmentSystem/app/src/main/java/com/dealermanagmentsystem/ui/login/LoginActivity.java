package com.dealermanagmentsystem.ui.login;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dealermanagmentsystem.BuildConfig;
import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SpinnerCustomAdapter;
import com.dealermanagmentsystem.adapter.SpinnerEndPointAdapter;
import com.dealermanagmentsystem.constants.Constants;
import com.dealermanagmentsystem.constants.ConstantsUrl;
import com.dealermanagmentsystem.data.model.endpoint.Body;
import com.dealermanagmentsystem.data.model.endpoint.EndPointResponse;
import com.dealermanagmentsystem.data.model.login.Company;
import com.dealermanagmentsystem.data.model.login.LoginResponse;
import com.dealermanagmentsystem.data.model.login.Record;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.home.HomeActivity;
import com.dealermanagmentsystem.ui.splash.SplashActivity;
import com.dealermanagmentsystem.utils.Utils;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.KEY_COMPANIES;
import static com.dealermanagmentsystem.constants.Constants.KEY_CURRENT_COMPANY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_CURRENT_COMPANY_NAME;
import static com.dealermanagmentsystem.constants.Constants.KEY_IS_ADMIN;
import static com.dealermanagmentsystem.constants.Constants.KEY_MODULES;
import static com.dealermanagmentsystem.constants.Constants.KEY_ROLE;
import static com.dealermanagmentsystem.constants.Constants.KEY_TEAMS;
import static com.dealermanagmentsystem.constants.Constants.KEY_TOKEN;
import static com.dealermanagmentsystem.constants.Constants.KEY_URL;
import static com.dealermanagmentsystem.constants.Constants.KEY_USERNAME;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_EMAIL_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_IMAGE;
import static com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity.detailResponse;


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
    @BindView(R.id.txt_privacy_policy_link)
    TextView mLink;
    @BindView(R.id.txt_app_version)
    TextView mAppVersion;
   /* @BindView(R.id.sp_end_point)
    Spinner spEndPoint;
    @BindView(R.id.btn_recreate)
    Button btnRecreate;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = LoginActivity.this;
        ButterKnife.bind(this);
        showFullScreen();
        loginPresenter = new LoginPresenter(
                this);

        if (mLink != null) {
            mLink.setMovementMethod(LinkMovementMethod.getInstance());
        }

        mAppVersion.setText(Utils.getAppVersionName(this));

      /*  if (BuildConfig.DEBUG) {
            spEndPoint.setVisibility(View.VISIBLE);
            btnRecreate.setVisibility(View.VISIBLE);
            loginPresenter.getEndPoint(activity);
        } else {
            spEndPoint.setVisibility(View.GONE);
            btnRecreate.setVisibility(View.GONE);
            DMSPreference.setString(KEY_URL, "http://prod-api.turnright.tech/");
        }*/
    }

    @OnClick(R.id.btn_sign_in) //ButterKnife uses.
    public void launchHomePage() {
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
            DMSPreference.setInt(KEY_USER_ID, response.getUserId());
            DMSPreference.setString(KEY_USER_IMAGE, response.getImage());
            DMSPreference.setString(KEY_ROLE, response.getRole());
            DMSPreference.setBoolean(KEY_IS_ADMIN, response.getAdmin());

            Intent intent = new Intent(this, HomeActivity.class);
            final List<Record> records = response.getTeams().getRecords();
            Gson gson = new Gson();
            String json = gson.toJson(records);
            DMSPreference.setString(KEY_TEAMS, json);

            final List<String> module = response.getModule();
            Gson gsonModule = new Gson();
            String jsonModule = gsonModule.toJson(module);
            DMSPreference.setString(KEY_MODULES, jsonModule);

            final List<Company> companyList = response.getCompanyList();
            Gson gsonCompany = new Gson();
            String jsonCompany = gsonCompany.toJson(companyList);
            DMSPreference.setString(KEY_COMPANIES, jsonCompany);

            //Set the current company in login
            final Object companyId = response.getCompanyId();
            if (companyId instanceof List) {
                DMSPreference.setString(KEY_CURRENT_COMPANY_NAME, String.valueOf(((List) companyId).get(1)));
                DMSPreference.setString(KEY_CURRENT_COMPANY_ID, String.valueOf(((List) companyId).get(0)));
            }

            startActivity(intent);
            DMSToast.showLong(activity, "Logged in successfully");
        }
    }

    @Override
    public void onSuccessEndPoint(EndPointResponse response) {

       /* final List<Body> list = response.getBody();

        SpinnerEndPointAdapter customAdapter = new SpinnerEndPointAdapter(activity, list);
        spEndPoint.setAdapter(customAdapter);

        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUrl().equalsIgnoreCase(DMSPreference.getString(KEY_URL))) {
                pos = i;
            }
        }

        spEndPoint.setSelection(pos);

        spEndPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DMSPreference.setString(KEY_URL, list.get(i).getUrl());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
    }

    @OnClick(R.id.btn_recreate) //ButterKnife uses.
    public void recreateEndPoint() {
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Runtime.getRuntime().exit(0);
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}

