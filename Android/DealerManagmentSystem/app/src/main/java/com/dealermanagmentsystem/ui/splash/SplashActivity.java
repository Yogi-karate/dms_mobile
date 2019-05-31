package com.dealermanagmentsystem.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.home.HomeActivity;
import com.dealermanagmentsystem.ui.login.LoginActivity;
import com.dealermanagmentsystem.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dealermanagmentsystem.constants.Constants.KEY_TOKEN;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        showFullScreen();

       // Utils.addToDeviceCalendar("16/05/2019","17/05/2019","Title","Description","lOCATION",this);
        title.startAnimation(AnimationUtils.loadAnimation(this, R.anim.left_to_right));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(DMSPreference.getString(KEY_TOKEN))) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);
    }
}
