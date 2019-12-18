package com.dealermanagmentsystem.ui.enquiry.lead;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.LeadAdapter;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.event.LeadActionLostEvent;
import com.dealermanagmentsystem.event.LeadActionMoveEvent;
import com.dealermanagmentsystem.event.LeadActionWonEvent;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_STAGE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_STATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_NAME;
import static com.dealermanagmentsystem.constants.Constants.STAGE_BOOKED;
import static com.dealermanagmentsystem.constants.Constants.STAGE_COLD;
import static com.dealermanagmentsystem.constants.Constants.STAGE_HOT;
import static com.dealermanagmentsystem.constants.Constants.STAGE_WARM;
import static com.dealermanagmentsystem.constants.Constants.STATE_COMPLETED;
import static com.dealermanagmentsystem.constants.Constants.STATE_OVERDUE;
import static com.dealermanagmentsystem.constants.Constants.STATE_PLANNED;
import static com.dealermanagmentsystem.constants.Constants.STATE_TODAY;

public class LeadActivity extends BaseActivity implements ILeadView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    LeadsPresenter presenter;
    String strState;
    String strFrom;
    String strStage;
    String strId;
    String strName;
    LeadAdapter leadAdapter;
    SearchView mSearchView;
    @BindView(R.id.sw_filter)
    SwitchCompat cbMyLeads;
    @BindView(R.id.ll_filter)
    LinearLayout llFilterParent;
    @BindView(R.id.ll_filter_state)
    LinearLayout llFilterState;

    boolean isCheckedMyLeads = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        activity = LeadActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Leads");
        showBackButton();

        final Intent intent = getIntent();
        if (intent != null) {
            strFrom = intent.getStringExtra(EXTRA_FROM);
            if (strFrom.equalsIgnoreCase("home")) {
                strState = intent.getStringExtra(EXTRA_STATE);
                strStage = intent.getStringExtra(EXTRA_STAGE);
            } else {//from team page
                strId = intent.getStringExtra(EXTRA_USER_ID);
                strName = intent.getStringExtra(EXTRA_USER_NAME);
                strStage = STAGE_BOOKED;
                llFilterParent.setVisibility(View.GONE);
            }
        }

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        presenter = new LeadsPresenter(this);
        cbMyLeads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheckedMyLeads = isChecked;
                presenter.getLeads(activity, strState, strStage, isCheckedMyLeads);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (strFrom.equalsIgnoreCase("home")) {
            presenter.getLeads(activity, strState, strStage, isCheckedMyLeads);
        } else {
            presenter.getLeadsBooked(activity, strId);
        }
    }

    @OnClick(R.id.ll_filter_state) //ButterKnife uses.
    public void showDialogFilter() {

        final View view = getLayoutInflater().inflate(R.layout.dialog_filter_leads, null);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(view);


       /* final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_filter_leads);
      */
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        final TextView stateOverdue = view.findViewById(R.id.state_overdue);
        final TextView statePlanned = view.findViewById(R.id.state_planned);
        final TextView stateToday = view.findViewById(R.id.state_today);
        final TextView stateNoActivities = view.findViewById(R.id.state_no_activities);

        final TextView stageCold = view.findViewById(R.id.stage_cold);
        final TextView stageWarm = view.findViewById(R.id.stage_warm);
        final TextView stageHot = view.findViewById(R.id.stage_hot);
        final TextView stageBooked = view.findViewById(R.id.stage_booked);
        final FloatingActionButton fabGo = view.findViewById(R.id.fab_go);


        if (strState.equalsIgnoreCase(STATE_OVERDUE)) {
            setStateOverdue(stateOverdue, statePlanned, stateToday, stateNoActivities);
        } else if (strState.equalsIgnoreCase(STATE_TODAY)) {
            setStateToday(stateOverdue, statePlanned, stateToday, stateNoActivities);
        } else if (strState.equalsIgnoreCase(STATE_PLANNED)) {
            setStatePlanned(stateOverdue, statePlanned, stateToday, stateNoActivities);
        } else if (strState.equalsIgnoreCase(STATE_COMPLETED)) {
            setStateCompleted(stateOverdue, statePlanned, stateToday, stateNoActivities);
        }

        if (strStage.equalsIgnoreCase(STAGE_COLD)) {
            setStageCold(stageCold, stageWarm, stageHot, stageBooked);
        } else if (strStage.equalsIgnoreCase(STAGE_WARM)) {
            setStageWarm(stageCold, stageWarm, stageHot, stageBooked);
        } else if (strStage.equalsIgnoreCase(STAGE_HOT)) {
            setStageHot(stageCold, stageWarm, stageHot, stageBooked);
        } else if (strStage.equalsIgnoreCase(STAGE_BOOKED)) {
            setStageBooked(stageCold, stageWarm, stageHot, stageBooked);
        }

        stateOverdue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStateOverdue(stateOverdue, statePlanned, stateToday, stateNoActivities);
            }
        });
        statePlanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatePlanned(stateOverdue, statePlanned, stateToday, stateNoActivities);
            }
        });
        stateToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStateToday(stateOverdue, statePlanned, stateToday, stateNoActivities);
            }
        });
        stateNoActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStateCompleted(stateOverdue, statePlanned, stateToday, stateNoActivities);
            }
        });
        stageCold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStageCold(stageCold, stageWarm, stageHot, stageBooked);
            }
        });
        stageWarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStageWarm(stageCold, stageWarm, stageHot, stageBooked);
            }
        });
        stageHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStageHot(stageCold, stageWarm, stageHot, stageBooked);
            }
        });
        stageBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStageBooked(stageCold, stageWarm, stageHot, stageBooked);
            }
        });

        fabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getLeads(activity, strState, strStage, isCheckedMyLeads);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setStageBooked(TextView stageCold, TextView stageWarm, TextView stageHot, TextView stageBooked) {
        strStage = STAGE_BOOKED;
        stageCold.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageWarm.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageHot.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageBooked.setBackground(getResources().getDrawable(R.drawable.btn_purple));
    }


    public void setStageHot(TextView stageCold, TextView stageWarm, TextView stageHot, TextView stageBooked) {
        strStage = STAGE_HOT;
        stageCold.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageWarm.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageHot.setBackground(getResources().getDrawable(R.drawable.btn_purple));
        stageBooked.setBackground(getResources().getDrawable(R.drawable.btn_white));
    }

    public void setStageWarm(TextView stageCold, TextView stageWarm, TextView stageHot, TextView stageBooked) {
        strStage = STAGE_WARM;
        stageCold.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageWarm.setBackground(getResources().getDrawable(R.drawable.btn_purple));
        stageHot.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageBooked.setBackground(getResources().getDrawable(R.drawable.btn_white));
    }

    public void setStageCold(TextView stageCold, TextView stageWarm, TextView stageHot, TextView stageBooked) {
        strStage = STAGE_COLD;
        stageCold.setBackground(getResources().getDrawable(R.drawable.btn_purple));
        stageWarm.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageHot.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stageBooked.setBackground(getResources().getDrawable(R.drawable.btn_white));
    }

    public void setStateOverdue(TextView stateOverdue, TextView statePlanned, TextView stateToday, TextView stateNoActivities) {
        strState = STATE_OVERDUE;
        stateOverdue.setBackground(getResources().getDrawable(R.drawable.btn_purple));
        statePlanned.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stateToday.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stateNoActivities.setBackground(getResources().getDrawable(R.drawable.btn_white));
    }

    public void setStateToday(TextView stateOverdue, TextView statePlanned, TextView stateToday, TextView stateNoActivities) {
        strState = STATE_TODAY;
        stateOverdue.setBackground(getResources().getDrawable(R.drawable.btn_white));
        statePlanned.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stateToday.setBackground(getResources().getDrawable(R.drawable.btn_purple));
        stateNoActivities.setBackground(getResources().getDrawable(R.drawable.btn_white));
    }

    public void setStatePlanned(TextView stateOverdue, TextView statePlanned, TextView stateToday, TextView stateNoActivities) {
        strState = STATE_PLANNED;
        stateOverdue.setBackground(getResources().getDrawable(R.drawable.btn_white));
        statePlanned.setBackground(getResources().getDrawable(R.drawable.btn_purple));
        stateToday.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stateNoActivities.setBackground(getResources().getDrawable(R.drawable.btn_white));
    }

    public void setStateCompleted(TextView stateOverdue, TextView statePlanned, TextView stateToday, TextView stateNoActivities) {
        strState = STATE_COMPLETED;
        stateOverdue.setBackground(getResources().getDrawable(R.drawable.btn_white));
        statePlanned.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stateToday.setBackground(getResources().getDrawable(R.drawable.btn_white));
        stateNoActivities.setBackground(getResources().getDrawable(R.drawable.btn_purple));
    }

    @Override
    public void onSuccessLeads(EnquiryResponse enquiryResponse) {
        if (enquiryResponse.getRecords() != null) {
            leadAdapter = new LeadAdapter(this, enquiryResponse.getRecords(), strStage);
            recyclerView.setAdapter(leadAdapter);
        }
        if (strFrom.equalsIgnoreCase("home")) {
            showTile(strState + "/" + strStage + "-" + String.valueOf(enquiryResponse.getLength()));
        } else {
            showTile(strName + "/" + strStage + "-" + String.valueOf(enquiryResponse.getLength()));
        }
    }

    @Override
    public void onSuccessWonLost(CommonResponse commonResponse) {
        if (commonResponse.getSuccess()) {
            presenter.getLeads(activity, strState, strStage, isCheckedMyLeads);
        } else if (!TextUtils.isEmpty(commonResponse.getError())) {
            onError("Could not submit response");
        }
    }

    @Override
    public void onSuccessLostReason(Response response, String id) {
        final List<Record> records = response.getRecords();
        if (records.isEmpty()) {
            onError("No lost reasons found, try after sometime");
        } else {
            showLostDialog(id, records);
        }
    }

    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onMoveTo(LeadActionMoveEvent event) {
        String leadId = event.getCastedObject();
        presenter.getStage(activity, leadId);
    }

    @Override
    public void onSuccessGetStages(Response response, String id) {
        final List<Record> records = response.getRecords();
        if (records.isEmpty()) {
            onError("No Stages found, try after sometime");
        } else {
            showStagesDialog(id, records);
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onMarkWon(LeadActionWonEvent event) {
        String id = event.getCastedObject();
        presenter.markWonLost(activity, id, "Won", 0);
    }


    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onLost(LeadActionLostEvent event) {
        String id = event.getCastedObject();
        presenter.getLostReason(activity, id);
    }

    private void showLostDialog(final String id, final List<Record> records) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select a lost reason");

        ArrayList<String> reasonList = new ArrayList<String>();

        for (int i = 0; i < records.size(); i++) {
            reasonList.add(records.get(i).getName());
        }

        final CharSequence[] reason = reasonList.toArray(new String[reasonList.size()]);

        builder.setItems(reason, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                presenter.markWonLost(activity, id, "Lost", records.get(pos).getId());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showStagesDialog(final String leadId, final List<Record> records) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Move to different stage");

        ArrayList<String> stageList = new ArrayList<String>();

        for (int i = 0; i < records.size(); i++) {
            stageList.add(records.get(i).getName());
        }

        final CharSequence[] reason = stageList.toArray(new String[stageList.size()]);

        builder.setItems(reason, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                presenter.moveStage(activity, leadId, records.get(pos).getId());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) mSearch.getActionView();

        EditText searchEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //searchEditText.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey));
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.textPrimary));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.textSecondary));

        mSearchView.setQueryHint("Search by name and customer");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                leadAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
