package com.dealermanagmentsystem.ui.insurance.lead;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.InsuranceLeadAdapter;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.event.LeadActionLostEvent;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_STATE;

public class InsuranceLeadActivity extends BaseActivity implements IInsuranceLeadView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;

    InsuranceLeadsPresenter presenter;
    String strState;

    InsuranceLeadAdapter insuranceLeadAdapter;

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

        activity = InsuranceLeadActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Insurance Leads");
        showBackButton();

        final Intent intent = getIntent();
        if (intent != null) {
            strState = intent.getStringExtra(EXTRA_STATE);
        }

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        presenter = new InsuranceLeadsPresenter(this);

        cbMyLeads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheckedMyLeads = isChecked;
                presenter.getInsuranceLeads(activity, strState, isCheckedMyLeads);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getInsuranceLeads(activity, strState, isCheckedMyLeads);
    }

    @Override
    public void onSuccessInsuranceLeads(EnquiryResponse enquiryResponse) {
        if (enquiryResponse.getRecords() != null) {
            insuranceLeadAdapter = new InsuranceLeadAdapter(this, enquiryResponse.getRecords());
            recyclerView.setAdapter(insuranceLeadAdapter);
        }
        showTile(strState + "-" + String.valueOf(enquiryResponse.getLength()));

    }

    @Override
    public void onSuccessLost(CommonResponse commonResponse) {
        if (commonResponse.getSuccess()) {
            presenter.getInsuranceLeads(activity, strState, isCheckedMyLeads);
        } else if (!TextUtils.isEmpty(commonResponse.getError())) {
            onError("Could not submit response");
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onLost(LeadActionLostEvent event) {
        String id = event.getCastedObject();
        presenter.getLostReason(activity, id);
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
                presenter.markLost(activity, id, "Lost", records.get(pos).getId());
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
                insuranceLeadAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

   /* private void getCallDetails(Activity activity) {
        StringBuffer sb = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        } else {
            Cursor cur = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC limit 1;");

            int number = cur.getColumnIndex(CallLog.Calls.NUMBER);
            int duration = cur.getColumnIndex(CallLog.Calls.DURATION);
            sb.append("Call Details : \n");
            while (cur.moveToNext()) {
                String phNumber = cur.getString(number);
                String callDuration = cur.getString(duration);
                String dir = null;

                sb.append("\nPhone Number:--- " + phNumber + " \nCall duration in sec :--- " + callDuration);
                sb.append("\n----------------------------------");
            }
            cur.close();
        }
    }*/

}
