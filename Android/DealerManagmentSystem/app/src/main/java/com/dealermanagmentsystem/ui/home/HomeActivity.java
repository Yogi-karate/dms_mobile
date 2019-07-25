package com.dealermanagmentsystem.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SaleOrderAdapter;
import com.dealermanagmentsystem.adapter.TasksAdapter;
import com.dealermanagmentsystem.adapter.TeamAdapter;
import com.dealermanagmentsystem.constants.Constants;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.leadoverview.LeadOverviewResponse;
import com.dealermanagmentsystem.data.model.login.Record;
import com.dealermanagmentsystem.data.model.saleorder.saleoverview.SaleOverviewResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.data.model.teamdetail.Result;
import com.dealermanagmentsystem.data.model.teamdetail.TeamDetailResponse;
import com.dealermanagmentsystem.event.TasksCompleteEvent;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.enquirysubenquirylist.EnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.lead.LeadActivity;
import com.dealermanagmentsystem.ui.enquiry.tasks.TasksActivity;
import com.dealermanagmentsystem.ui.saleorder.SaleOrderActivity;
import com.dealermanagmentsystem.utils.ImageLoad;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.dealermanagmentsystem.utils.ui.DMSTypeFace;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.CREATE_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_STAGE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_STATE;
import static com.dealermanagmentsystem.constants.Constants.KEY_FCM_TOKEN;
import static com.dealermanagmentsystem.constants.Constants.KEY_FCM_TOKEN_SET;
import static com.dealermanagmentsystem.constants.Constants.KEY_NIGHT_MODE;
import static com.dealermanagmentsystem.constants.Constants.KEY_ROLE;
import static com.dealermanagmentsystem.constants.Constants.KEY_TEAMS;
import static com.dealermanagmentsystem.constants.Constants.KEY_USERNAME;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_EMAIL_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_IMAGE;
import static com.dealermanagmentsystem.constants.Constants.QUOTATION;
import static com.dealermanagmentsystem.constants.Constants.STAGE_BOOKED;
import static com.dealermanagmentsystem.constants.Constants.STAGE_COLD;
import static com.dealermanagmentsystem.constants.Constants.STAGE_HOT;
import static com.dealermanagmentsystem.constants.Constants.STAGE_WARM;
import static com.dealermanagmentsystem.constants.Constants.STATE_OVERDUE;
import static com.dealermanagmentsystem.constants.Constants.STATE_PLANNED;
import static com.dealermanagmentsystem.constants.Constants.STATE_TODAY;
import static com.dealermanagmentsystem.constants.Constants.SUB_ENQUIRY;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IHomeView {
    Activity activity;
    HomePresenter presenter;

    @BindView(R.id.fab_create_enquiry)
    FloatingActionButton fabCreateEnquiry;
    @BindView(R.id.chart)
    PieChart chart;
    /* @BindView(R.id.chart_sale_order)
     PieChart salesChart;*/
    @BindView(R.id.card_view_title)
    CardView cardView;
    @BindView(R.id.img_title)
    ImageView imageTitle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.txt_count_cold)
    TextView txtCold;
    @BindView(R.id.txt_count_warm)
    TextView txtWarm;
    @BindView(R.id.txt_count_hot)
    TextView txtHot;
    @BindView(R.id.txt_count_booked)
    TextView txtBooked;
    @BindView(R.id.ll_cold)
    LinearLayout llCold;
    @BindView(R.id.ll_warm)
    LinearLayout llWarm;
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.ll_booked)
    LinearLayout llBooked;
    String strState;
    /* @BindView(R.id.recycler_View_tasks)
     RecyclerView recyclerViewTasks;*/
    // TasksAdapter tasksAdapter;
    @BindView(R.id.tasks_title)
    TextView txtTasksTitle;
    @BindView(R.id.tasks_more)
    TextView txtTasksMore;
    @BindView(R.id.cardView_tasks)
    CardView cardViewTasks;
   /* @BindView(R.id.txt_delivery_count)
    TextView txtDeliveryCount;
    @BindView(R.id.txt_invoice_count)
    TextView txtInvoiceCount;
    @BindView(R.id.cv_delivery_count)
    CardView cvDeliveryCount;
    @BindView(R.id.cv_invoice_count)
    CardView cvInvoiceCount;*/

    @BindView(R.id.overdue)
    TextView txtLegendOverdue;
    @BindView(R.id.today)
    TextView txtLegendToday;
    @BindView(R.id.planned)
    TextView txtLegendPlanned;
    @BindView(R.id.recycler_view_teams)
    RecyclerView recyclerTeams;
    @BindView(R.id.txt_team_title)
    TextView txtTeamTitle;
    @BindView(R.id.cardView_teams)
    CardView cardViewTeams;

    Integer overdueCold, overdueWarm, overdueHot, overdueBooked;
    Integer todayCold, todayWarm, todayHot, todayBooked;
    Integer plannedCold, plannedWarm, plannedHot, plannedBooked;
    List<Record> recordsTeamList;
    String strRole;
    TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (DMSPreference.getBoolean(KEY_NIGHT_MODE)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        activity = HomeActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("DashBoard");

        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("DashBoard");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        strRole = DMSPreference.getString(KEY_ROLE, "user");
        if (!strRole.equalsIgnoreCase("user")) {
            Gson gson = new Gson();
            String json = DMSPreference.getString(KEY_TEAMS);
            Type type = new TypeToken<List<Record>>() {
            }.getType();
            recordsTeamList = gson.fromJson(json, type);
            cardViewTeams.setVisibility(View.VISIBLE);
        } else {
            cardViewTeams.setVisibility(View.GONE);
        }

        setNavigationView();
        presenter = new HomePresenter(this);
        sendIdToServer();
    }

    private void setNavigationView() {
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

    private void sendIdToServer() {
        if (!DMSPreference.getBoolean(KEY_FCM_TOKEN_SET)) {
            presenter.sendFcmToken(activity, DMSPreference.getString(KEY_FCM_TOKEN));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cardView.setVisibility(View.GONE);
        presenter.getLeadsOverview(activity);
        //  presenter.getSalesOverview(activity);
        presenter.getTasksOverview(activity);
        // presenter.getDeliveryCount(activity);
        // presenter.getInvoiceCount(activity);
        if (!strRole.equalsIgnoreCase("user")) {
            txtTeamTitle.setText(recordsTeamList.get(0).getName());
            presenter.getTeamDetailList(activity, String.valueOf(recordsTeamList.get(0).getId()));
        }
    }

    @Override
    public void onSuccessLeadOverview(List<LeadOverviewResponse> leadOverviewResponse) {
        setPieChartLeadData(leadOverviewResponse);
    }

    @Override
    public void onSuccessSalesOverview(List<SaleOverviewResponse> saleOverviewResponses) {
        //  setPieChartSalesData(saleOverviewResponses);
    }

    @Override
    public void onSuccessTasks(final List<TasksResponse> tasks) {

        if (tasks.size() == 0) {
            cardViewTasks.setVisibility(View.GONE);
        } else {
            cardViewTasks.setVisibility(View.VISIBLE);
            //txtTasksTitle.setText("Tasks (" + String.valueOf(tasks.size()) + ")");

   /*         LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewTasks.setHasFixedSize(true);
            recyclerViewTasks.setLayoutManager(gridLayoutManager);
            if (tasks != null) {
                tasksAdapter = new TasksAdapter(this, tasks);
                recyclerViewTasks.setAdapter(tasksAdapter);
            }*/

            txtTasksMore.setText(String.valueOf(tasks.size()));
            txtTasksMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, TasksActivity.class);
                    intent.putExtra(EXTRA_ACTIVITY_COMING_FROM, "Home");
                    activity.startActivity(intent);
                }
            });
        }
    }

    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onComplete(TasksCompleteEvent event) {
        String taskId = event.getCastedObject();
        showDialogFeedback(taskId);
    }

    private void showDialogFeedback(final String taskId) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_task_feedback, null);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        final EditText feedback = view.findViewById(R.id.feedback);
        final Button ok = view.findViewById(R.id.ok);
        final Button close = view.findViewById(R.id.close);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strFeedback = feedback.getText().toString();
                if (TextUtils.isEmpty(strFeedback)) {
                    DMSToast.showLong(activity, "Please enter feedback");
                } else {
                    presenter.setFeedback(activity, taskId, strFeedback);
                    dialog.dismiss();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Rect displayRectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onSuccessFeedBack(CommonResponse commonResponse) {
        if (commonResponse.getMessage().equalsIgnoreCase(Constants.SUCCESS)) {
            DMSToast.showLong(activity, "Submitted successfully");
            presenter.getTasksOverview(activity);
        }
    }


    @Override
    public void onSuccessDeliveryCount(String count) {
       /* txtDeliveryCount.setText(count);
        cvDeliveryCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DeliveryActivity.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onSuccessInvoiceCount(String count) {
      /*  txtInvoiceCount.setText(count);
        cvInvoiceCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SaleOrderActivity.class);
                intent.putExtra(EXTRA_SALE_TYPE, TO_INVOICE);
                intent.putExtra(EXTRA_SALE_TYPE_ID, "to invoice");
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @Override
    public void onSuccessToken() {
    }

    public void setPieChartLeadData(List<LeadOverviewResponse> leadOverviewResponse) {

        overdueCold = leadOverviewResponse.get(0).getResult().get(0).getStageIdCount();
        overdueWarm = leadOverviewResponse.get(0).getResult().get(1).getStageIdCount();
        overdueHot = leadOverviewResponse.get(0).getResult().get(2).getStageIdCount();
        overdueBooked = leadOverviewResponse.get(0).getResult().get(3).getStageIdCount();
        int overdue = overdueCold + overdueWarm + overdueHot + overdueBooked;

        todayCold = leadOverviewResponse.get(1).getResult().get(0).getStageIdCount();
        todayWarm = leadOverviewResponse.get(1).getResult().get(1).getStageIdCount();
        todayHot = leadOverviewResponse.get(1).getResult().get(2).getStageIdCount();
        todayBooked = leadOverviewResponse.get(1).getResult().get(3).getStageIdCount();
        int today = todayCold + todayWarm + todayHot + todayBooked;

        plannedCold = leadOverviewResponse.get(2).getResult().get(0).getStageIdCount();
        plannedWarm = leadOverviewResponse.get(2).getResult().get(1).getStageIdCount();
        plannedHot = leadOverviewResponse.get(2).getResult().get(2).getStageIdCount();
        plannedBooked = leadOverviewResponse.get(2).getResult().get(3).getStageIdCount();
        int planned = plannedCold + plannedWarm + plannedHot + plannedBooked;

        int total = overdue + today + planned;

        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterTextTypeface(DMSTypeFace.getTypeface(activity));
        chart.setCenterTextSize(16f);//center size
        chart.setCenterText(String.valueOf(total));
        chart.setCenterTextColor(getResources().getColor(R.color.textPrimary));
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(getResources().getColor(R.color.card_bg));
        chart.setTransparentCircleColor(getResources().getColor(R.color.card_bg));
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(50f);
        chart.setTransparentCircleRadius(51f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);


        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTypeface(DMSTypeFace.getTypeface(activity));

        chart.getLegend().setEnabled(false);
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTypeface(DMSTypeFace.getTypeface(activity));
        chart.setEntryLabelTextSize(0f);//title size

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        // if (overdue > 0) {
        entries.add(new PieEntry(overdue,
                String.valueOf(overdue) + " Overdue",
                getResources().getDrawable(R.drawable.ic_overdue)));
        colors.add(getResources().getColor(R.color.light_orange));
        //  }

        //   if (today > 0) {
        entries.add(new PieEntry(today,
                String.valueOf(today) + " Today",
                getResources().getDrawable(R.drawable.ic_today)));
        colors.add(getResources().getColor(R.color.light_yellow));
        //  }

        //if (planned > 0) {
        entries.add(new PieEntry(planned,
                String.valueOf(planned) + " Planned",
                getResources().getDrawable(R.drawable.ic_enquiry)));
        colors.add(getResources().getColor(R.color.light_blue));
        //}

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(0f);//value size
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(DMSTypeFace.getTypeface(activity));
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);
        chart.invalidate();

        txtLegendOverdue.setText(String.valueOf(overdue) + " Overdue");
        txtLegendToday.setText(String.valueOf(today) + " Today");
        txtLegendPlanned.setText(String.valueOf(planned) + " Planned");

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;

                if (h.getX() == 0) {
                    launchOverdue();
                } else if (h.getX() == 1) {
                    launchToday();
                } else {
                    launchPlanned();
                }

                Log.i("VAL SELECTED",
                        "Value: " + e.getX() + ", index: " + h.getX()
                                + ", DataSet index: " + h.getDataSetIndex());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    @OnClick(R.id.legend_overdue) //ButterKnife uses.
    public void launchOverdue() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(activity,
                R.anim.move_left_in_activity);
        if (cardView.getVisibility() != View.VISIBLE) {
            cardView.setVisibility(View.VISIBLE);
        }
        cardView.setAnimation(animation);
        llTitle.setBackgroundColor(getResources().getColor(R.color.light_orange));
        title.setText("Overdue");
        imageTitle.setImageResource(R.drawable.ic_overdue);
        strState = STATE_OVERDUE;
        txtCold.setText(String.valueOf(overdueCold));
        txtWarm.setText(String.valueOf(overdueWarm));
        txtHot.setText(String.valueOf(overdueHot));
        txtBooked.setText(String.valueOf(overdueBooked));
    }

    @OnClick(R.id.legend_today) //ButterKnife uses.
    public void launchToday() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(activity,
                R.anim.move_left_in_activity);
        if (cardView.getVisibility() != View.VISIBLE) {
            cardView.setVisibility(View.VISIBLE);
        }
        cardView.setAnimation(animation);
        llTitle.setBackgroundColor(getResources().getColor(R.color.light_yellow));
        title.setText("Today");
        imageTitle.setImageResource(R.drawable.ic_today);
        strState = STATE_TODAY;
        txtCold.setText(String.valueOf(todayCold));
        txtWarm.setText(String.valueOf(todayWarm));
        txtHot.setText(String.valueOf(todayHot));
        txtBooked.setText(String.valueOf(todayBooked));
    }

    @OnClick(R.id.legend_planned) //ButterKnife uses.
    public void launchPlanned() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(activity,
                R.anim.move_left_in_activity);
        if (cardView.getVisibility() != View.VISIBLE) {
            cardView.setVisibility(View.VISIBLE);
        }
        cardView.setAnimation(animation);
        llTitle.setBackgroundColor(getResources().getColor(R.color.light_blue));
        title.setText("Planned");
        imageTitle.setImageResource(R.drawable.ic_enquiry);
        strState = STATE_PLANNED;
        txtCold.setText(String.valueOf(plannedCold));
        txtWarm.setText(String.valueOf(plannedWarm));
        txtHot.setText(String.valueOf(plannedHot));
        txtBooked.setText(String.valueOf(plannedBooked));
    }


  /*  public void setPieChartSalesData(List<SaleOverviewResponse> leadOverviewResponse) {
        final List<Result> result = leadOverviewResponse.get(0).getResult();

        Integer quotation = 0;
        Integer booked = 0;
        Integer cancel = 0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getState().equalsIgnoreCase("draft")) {
                quotation = result.get(i).getStateCount();
            }
            if (result.get(i).getState().equalsIgnoreCase("sale")) {
                booked = result.get(i).getStateCount();
            }
            if (result.get(i).getState().equalsIgnoreCase("cancel")) {
                cancel = result.get(i).getStateCount();
            }
        }


        int total = quotation + booked + cancel;

        salesChart.setUsePercentValues(false);
        salesChart.getDescription().setEnabled(false);
        salesChart.setExtraOffsets(5, 10, 5, 5);
        salesChart.setDragDecelerationFrictionCoef(0.95f);
        salesChart.setCenterTextTypeface(DMSTypeFace.getTypeface(activity));
        salesChart.setCenterTextSize(16f);//center size
        salesChart.setCenterText(String.valueOf(total));
        salesChart.setDrawHoleEnabled(true);
        salesChart.setHoleColor(getResources().getColor(R.color.white));
        salesChart.setTransparentCircleColor(Color.WHITE);
        salesChart.setTransparentCircleAlpha(110);
        salesChart.setHoleRadius(50f);
        salesChart.setTransparentCircleRadius(51f);
        salesChart.setDrawCenterText(true);
        salesChart.setRotationAngle(0);
        salesChart.setRotationEnabled(true);
        salesChart.setHighlightPerTapEnabled(true);

        salesChart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = salesChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTypeface(DMSTypeFace.getTypeface(activity));

        salesChart.setEntryLabelColor(Color.WHITE);
        salesChart.setEntryLabelTypeface(DMSTypeFace.getTypeface(activity));
        salesChart.setEntryLabelTextSize(0f);//title size

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        entries.add(new PieEntry(quotation,
                String.valueOf(quotation) + " Quotation",
                getResources().getDrawable(R.drawable.ic_overdue)));
        colors.add(getResources().getColor(R.color.light_orange));


        entries.add(new PieEntry(booked,
                String.valueOf(booked) + " Booked",
                getResources().getDrawable(R.drawable.ic_today)));
        colors.add(getResources().getColor(R.color.light_yellow));

        entries.add(new PieEntry(cancel,
                String.valueOf(cancel) + " Cancel",
                getResources().getDrawable(R.drawable.ic_enquiry)));
        colors.add(getResources().getColor(R.color.light_blue));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(0f);//value size
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(DMSTypeFace.getTypeface(activity));
        salesChart.setData(data);

        // undo all highlights
        salesChart.highlightValues(null);
        salesChart.invalidate();

        salesChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                Intent intent = new Intent(activity, SaleOrderActivity.class);
                if (h.getX() == 0) {
                    intent.putExtra(EXTRA_SALE_TYPE, QUOTATION);
                    intent.putExtra(EXTRA_SALE_TYPE_ID, "draft");
                    startActivity(intent);
                } else if (h.getX() == 1) {
                    intent.putExtra(EXTRA_SALE_TYPE, BOOKED);
                    intent.putExtra(EXTRA_SALE_TYPE_ID, "sale");
                    startActivity(intent);
                } else {
                    intent.putExtra(EXTRA_SALE_TYPE, CANCEL);
                    intent.putExtra(EXTRA_SALE_TYPE_ID, "cancel");
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }*/

    @OnClick(R.id.ll_cold) //ButterKnife uses.
    public void launchCold() {
        Intent intent = new Intent(this, LeadActivity.class);
        intent.putExtra(EXTRA_STATE, strState);
        intent.putExtra(EXTRA_STAGE, STAGE_COLD);
        intent.putExtra(EXTRA_FROM, "home");
        startActivity(intent);
    }

    @OnClick(R.id.ll_warm) //ButterKnife uses.
    public void launchWarm() {
        Intent intent = new Intent(this, LeadActivity.class);
        intent.putExtra(EXTRA_STATE, strState);
        intent.putExtra(EXTRA_STAGE, STAGE_WARM);
        intent.putExtra(EXTRA_FROM, "home");
        startActivity(intent);
    }

    @OnClick(R.id.ll_hot) //ButterKnife uses.
    public void launchHot() {
        Intent intent = new Intent(this, LeadActivity.class);
        intent.putExtra(EXTRA_STATE, strState);
        intent.putExtra(EXTRA_STAGE, STAGE_HOT);
        intent.putExtra(EXTRA_FROM, "home");
        startActivity(intent);
    }

    @OnClick(R.id.ll_booked) //ButterKnife uses.
    public void launchBooked() {
        Intent intent = new Intent(this, LeadActivity.class);
        intent.putExtra(EXTRA_STATE, strState);
        intent.putExtra(EXTRA_STAGE, STAGE_BOOKED);
        intent.putExtra(EXTRA_FROM, "home");
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.enquiry) {
            Intent intent = new Intent(this, EnquiryActivity.class);
            intent.putExtra(EXTRA_ENQUIRY, ENQUIRY);
            startActivity(intent);
        } else if (id == R.id.sub_enquiry) {
            Intent intent = new Intent(this, EnquiryActivity.class);
            intent.putExtra(EXTRA_ENQUIRY, SUB_ENQUIRY);
            startActivity(intent);
        } else if (id == R.id.sales_order) {
            Intent intent = new Intent(this, SaleOrderActivity.class);
            intent.putExtra(EXTRA_SALE_TYPE, QUOTATION);
            intent.putExtra(EXTRA_SALE_TYPE_ID, "draft");
            startActivity(intent);
        } /*else if (id == R.id.follow_up) {
            DMSToast.showLong(activity, "Coming Soon..");
        } */ else if (id == R.id.change_theme) {
            if (DMSPreference.getBoolean(KEY_NIGHT_MODE)) {
                DMSPreference.setBoolean(KEY_NIGHT_MODE, false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            } else {
                DMSPreference.setBoolean(KEY_NIGHT_MODE, true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }

        } else if (id == R.id.logout) {
            logOutDialog(activity);
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
            logOutDialog(activity);
        }
    }

    @OnClick(R.id.fab_create_enquiry) //ButterKnife uses.
    public void openCreateEnquiry() {
        Intent intent = new Intent(this, CreateEnquiryActivity.class);
        intent.putExtra(EXTRA_ENQUIRY, CREATE_ENQUIRY);
        startActivityForResult(intent, 2);
    }


    @OnClick(R.id.ll_team_dialog) //ButterKnife uses.
    public void openTeamDialog() {
        showTeamDialog();
    }

    private void showTeamDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select a team");

        ArrayList<String> reasonList = new ArrayList<String>();

        for (int i = 0; i < recordsTeamList.size(); i++) {
            reasonList.add(recordsTeamList.get(i).getName());
        }

        final CharSequence[] reason = reasonList.toArray(new String[reasonList.size()]);

        builder.setItems(reason, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                txtTeamTitle.setText(recordsTeamList.get(pos).getName());
                if (!strRole.equalsIgnoreCase("user")) {
                    presenter.getTeamDetailList(activity, String.valueOf(recordsTeamList.get(pos).getId()));
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onSuccessTeamDetail(List<TeamDetailResponse> response) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerTeams.setHasFixedSize(true);
        recyclerTeams.setLayoutManager(gridLayoutManager);
        final List<Result> result = response.get(0).getResult();

        teamAdapter = new TeamAdapter(result, this);
        recyclerTeams.setAdapter(teamAdapter);

    }
}
