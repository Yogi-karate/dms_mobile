package com.dealermanagmentsystem.ui.enquiry.tasks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SpinnerCustomAdapter;
import com.dealermanagmentsystem.adapter.TasksAdapter;
import com.dealermanagmentsystem.constants.Constants;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.event.TasksCompleteEvent;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM_MODULE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_CREATE_ACTIVITY;

public class TasksActivity extends BaseActivity implements ITasksView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    Activity activity;
    String strLeadId, strFrom;
    TasksAdapter tasksAdapter;
    TasksPresenter presenter;
    @BindView(R.id.fab_create_task)
    FloatingActionButton fabCreateTasks;
    int strUserId;
    int dispositionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        activity = TasksActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Activity");
        showBackButton();

        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManagerCategories);

        final Intent intent = getIntent();
        if (intent != null) {
            strFrom = intent.getStringExtra(EXTRA_ACTIVITY_COMING_FROM);
            if (strFrom.equalsIgnoreCase("Leads")) {
                strLeadId = intent.getStringExtra(EXTRA_LEAD_ID);
                strUserId = (int) intent.getIntExtra(EXTRA_USER_ID, 0);
            } else if (strFrom.equalsIgnoreCase("ServiceLeads")) {
                strLeadId = intent.getStringExtra(EXTRA_LEAD_ID);
                strUserId = (int) intent.getIntExtra(EXTRA_USER_ID, 0);
            } else if (strFrom.equalsIgnoreCase("InsuranceLeads")) {
                strLeadId = intent.getStringExtra(EXTRA_LEAD_ID);
                strUserId = (int) intent.getIntExtra(EXTRA_USER_ID, 0);
            } else {
                fabCreateTasks.setVisibility(View.GONE);
            }
        }

        presenter = new TasksPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (strFrom.equalsIgnoreCase("Leads")) {
            presenter.getTasks(activity, strLeadId);
        } else if (strFrom.equalsIgnoreCase("ServiceLeads")) {
            presenter.getServiceTasks(activity, strLeadId);
        } else if (strFrom.equalsIgnoreCase("InsuranceLeads")) {
            presenter.getServiceTasks(activity, strLeadId);
        } else {
            presenter.getTasksOverview(activity);//from home
        }
    }

    @Override
    public void onSuccessTasks(List<TasksResponse> tasks) {
        if (tasks != null) {
            tasksAdapter = new TasksAdapter(this, tasks, strFrom);
            recyclerView.setAdapter(tasksAdapter);
        }
    }

    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onComplete(TasksCompleteEvent event) {
        String taskId = event.getCastedObject();
        if (strFrom.equalsIgnoreCase("ServiceLeads") || strFrom.equalsIgnoreCase("InsuranceLeads")) {
            presenter.getDisposition(activity, taskId);
        } else {
            showDialogFeedback(taskId, null);
        }

    }

    @Override
    public void onSuccessDisposition(Response response, String taskId) {
        showDialogFeedback(taskId, response);
    }

    private void showDialogFeedback(final String taskId, Response response) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_task_feedback, null);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        final EditText feedback = view.findViewById(R.id.feedback);
        final Button ok = view.findViewById(R.id.ok);
        final Button close = view.findViewById(R.id.close);
        final Spinner spDisposition = view.findViewById(R.id.sp_disposition);

        if (strFrom.equalsIgnoreCase("ServiceLeads") || strFrom.equalsIgnoreCase("InsuranceLeads")) {
            spDisposition.setVisibility(View.VISIBLE);
            final List<Record> dispositionRecords = response.getRecords();

            SpinnerCustomAdapter customAdapter = new SpinnerCustomAdapter(activity, dispositionRecords);
            spDisposition.setAdapter(customAdapter);

            spDisposition.setSelection(0);

            spDisposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    dispositionId = dispositionRecords.get(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else {
            spDisposition.setVisibility(View.GONE);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strFeedback = feedback.getText().toString();
                if (TextUtils.isEmpty(strFeedback)) {
                    DMSToast.showLong(activity, "Please enter feedback");
                } else {
                    if (strFrom.equalsIgnoreCase("ServiceLeads")) {
                        presenter.setServiceFeedback(activity, taskId, strFeedback,dispositionId);
                    } else if (strFrom.equalsIgnoreCase("InsuranceLeads")) {
                        presenter.setServiceFeedback(activity, taskId, strFeedback,dispositionId);
                    } else {
                        presenter.setFeedback(activity, taskId, strFeedback);
                    }

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
            if (strFrom.equalsIgnoreCase("Leads")) {
                presenter.getTasks(activity, strLeadId);
            } else if (strFrom.equalsIgnoreCase("ServiceLeads")) {
                presenter.getServiceTasks(activity, strLeadId);
            } else if (strFrom.equalsIgnoreCase("InsuranceLeads")) {
                presenter.getServiceTasks(activity, strLeadId);
            } else {
                presenter.getTasksOverview(activity);
            }
        }
    }


    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @OnClick(R.id.fab_create_task) //ButterKnife uses.
    public void openCreateTasks() {
        Intent intent = new Intent(this, TaskCreateActivity.class);
        intent.putExtra(EXTRA_LEAD_ID, strLeadId);
        intent.putExtra(EXTRA_USER_ID, strUserId);
        intent.putExtra(EXTRA_ACTIVITY_COMING_FROM, KEY_CREATE_ACTIVITY);
        intent.putExtra(EXTRA_ACTIVITY_COMING_FROM_MODULE, strFrom);
        startActivity(intent);
    }
}
