package com.dealermanagmentsystem.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.dialog.DefaultAlertDialog;
import com.dealermanagmentsystem.dialog.TwoButtonAlertDialogModel;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.enquirysubenquirylist.EnquiryActivity;
import com.dealermanagmentsystem.ui.login.LoginActivity;
import com.dealermanagmentsystem.ui.saleorder.SaleOrderActivity;
import com.dealermanagmentsystem.utils.ImageLoad;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.CREATE_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.KEY_TOKEN;
import static com.dealermanagmentsystem.constants.Constants.KEY_USERNAME;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_EMAIL_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_IMAGE;
import static com.dealermanagmentsystem.constants.Constants.SUB_ENQUIRY;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    @BindView(R.id.fab_create_enquiry)
    FloatingActionButton fabCreateEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        activity = HomeActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("DashBoard");

        showTile("DashBoard");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            applyFontToMenuItem(mi);
        }


        View headerView = navigationView.getHeaderView(0);
        TextView tvName = (TextView) headerView.findViewById(R.id.txt_name);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.txt_email);
        ImageView imgUser = (ImageView) headerView.findViewById(R.id.img_user);

        tvName.setText(DMSPreference.getString(KEY_USERNAME));
        tvEmail.setText(DMSPreference.getString(KEY_USER_EMAIL_ID));
        ImageLoad.loadImageBase64(DMSPreference.getString(KEY_USER_IMAGE), imgUser);

    }

    @OnClick(R.id.ll_enquiry) //ButterKnife uses.
    public void launchEnquiry() {
        Intent intent = new Intent(this, EnquiryActivity.class);
        intent.putExtra(EXTRA_ENQUIRY, ENQUIRY);
        startActivity(intent);
    }

    @OnClick(R.id.ll_sub_enquiry) //ButterKnife uses.
    public void launchSubEnquiry() {
        Intent intent = new Intent(this, EnquiryActivity.class);
        intent.putExtra(EXTRA_ENQUIRY, SUB_ENQUIRY);
        startActivity(intent);
    }

    @OnClick(R.id.ll_sale_order) //ButterKnife uses.
    public void launchSaleOrder() {
        Intent intent = new Intent(this, SaleOrderActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_follow_up) //ButterKnife uses.
    public void launchFollowUp() {
        DMSToast.showLong(activity, "Coming Soon..");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.enquiry) {
            launchEnquiry();
        } else if (id == R.id.sub_enquiry) {
            launchSubEnquiry();
        } else if (id == R.id.sales_order) {
            launchSaleOrder();
        } else if (id == R.id.follow_up) {
            launchFollowUp();
        } else if (id == R.id.logout) {
            logOutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            logOutDialog();
        }
    }

    public void logOutDialog() {
        String confirm = "Confirm";
        String logoutMessage = "Are you sure you want to Logout?";
        String yes = "OK";
        String cancel = "Cancel";
        TwoButtonAlertDialogModel dialogModel = new TwoButtonAlertDialogModel();
        dialogModel.setTitle(confirm);
        dialogModel.setMessage(logoutMessage);
        dialogModel.setPositiveButtonText(yes);
        dialogModel.setSecondButtonType(AlertDialog.BUTTON_NEGATIVE);
        dialogModel.setSecondButtonText(cancel);
        dialogModel.setCancellable(false);

        AlertDialog logOutDialog = DefaultAlertDialog.getTwoButtonDialog(this, dialogModel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        DMSPreference.setString(KEY_TOKEN, "");
                        DMSPreference.setString(KEY_USERNAME, "");
                        DMSPreference.setString(KEY_USER_EMAIL_ID, "");
                        DMSPreference.setString(KEY_USER_IMAGE, "");

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
            }
        });
        logOutDialog.show();
    }

    @OnClick(R.id.fab_create_enquiry) //ButterKnife uses.
    public void openCreateEnquiry() {
        Intent intent = new Intent(this, CreateEnquiryActivity.class);
        intent.putExtra(EXTRA_ENQUIRY, CREATE_ENQUIRY);
        startActivityForResult(intent, 2);
    }

}
