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
import android.widget.Button;
import android.widget.EditText;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.TasksAdapter;
import com.dealermanagmentsystem.constants.Constants;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.event.TasksCompleteEvent;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.CREATE_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_LISTS;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
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
        } else {
            presenter.getTasksOverview(activity);
        }
    }

    @Override
    public void onSuccessTasks(List<TasksResponse> tasks) {
        if (tasks != null) {
            tasksAdapter = new TasksAdapter(this, tasks);
            recyclerView.setAdapter(tasksAdapter);
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
            if (strFrom.equalsIgnoreCase("Leads")) {
                presenter.getTasks(activity, strLeadId);
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
        intent.putExtra(EXTRA_ACTIVITY_COMING_FROM, KEY_CREATE_ACTIVITY);
        startActivity(intent);
    }
}
