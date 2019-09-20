package com.dealermanagmentsystem.ui.enquiry.lead;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    LinearLayout llFilter;
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
                llFilter.setVisibility(View.GONE);
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
