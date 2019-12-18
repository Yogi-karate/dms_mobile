package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.event.TasksCompleteEvent;
import com.dealermanagmentsystem.preference.DMSPreference;
import com.dealermanagmentsystem.ui.base.BaseApplication;
import com.dealermanagmentsystem.ui.enquiry.tasks.TaskCreateActivity;

import java.util.List;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM_MODULE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_DATE_DEADLINE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_SUMMARY;
import static com.dealermanagmentsystem.constants.Constants.KEY_EDIT_ACTIVITY;
import static com.dealermanagmentsystem.constants.Constants.KEY_USER_ID;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<TasksResponse> mRecords;
    String mStrFrom;

    public TasksAdapter(Activity activity, List<TasksResponse> records, String strFrom) {
        this.activity = activity;
        this.mRecords = records;
        this.mStrFrom = strFrom;
    }

    @NonNull
    @Override
    public TasksAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tasks, viewGroup, false);
        return new TasksAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TasksAdapter.AdapterItemViewHolder itemViewHolder, final int i) {

        itemViewHolder.mSummary.setText(mRecords.get(i).getSummary());
        itemViewHolder.mDate.setText(mRecords.get(i).getDateDeadline());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            itemViewHolder.note.setText(Html.fromHtml(mRecords.get(i).getNote(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            itemViewHolder.note.setText(Html.fromHtml(mRecords.get(i).getNote()));
        }


        final Object activityTypeId = mRecords.get(i).getActivityTypeId();
        if (activityTypeId instanceof List) {
            itemViewHolder.activityType.setText(String.valueOf(((List) activityTypeId).get(1)) + ", " + mRecords.get(i).getModel());
        } else {
            itemViewHolder.activityType.setVisibility(View.GONE);
        }

        final Object userId = mRecords.get(i).getUserId();
        if (userId instanceof List) {
            itemViewHolder.userName.setText(String.valueOf(((List) userId).get(1)));
        } else {
            itemViewHolder.userName.setVisibility(View.GONE);
        }

        itemViewHolder.llComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.getEventBus().post(new TasksCompleteEvent(String.valueOf(mRecords.get(i).getId())));
            }
        });

        int strUserId = 0;
        Double d;
        if (userId instanceof List) {
            d = (Double) ((List) userId).get(0);
            strUserId = d.intValue();
        }

        final int finalStrUserId = strUserId;
        itemViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DMSPreference.getInt(KEY_USER_ID) == finalStrUserId){
                    Intent intent = new Intent(activity, TaskCreateActivity.class);
                    intent.putExtra(EXTRA_ACTIVITY_ID, String.valueOf(mRecords.get(i).getId()));
                    intent.putExtra(EXTRA_ACTIVITY_SUMMARY, mRecords.get(i).getSummary());
                    intent.putExtra(EXTRA_ACTIVITY_DATE_DEADLINE, mRecords.get(i).getDateDeadline());
                    intent.putExtra(EXTRA_ACTIVITY_COMING_FROM, KEY_EDIT_ACTIVITY);
                    intent.putExtra(EXTRA_ACTIVITY_COMING_FROM_MODULE, mStrFrom);
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mSummary;
        TextView mDate, activityType, userName, note;
        LinearLayout llComplete, llParent;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mSummary = (TextView) itemView.findViewById(R.id.summary);
            mDate = (TextView) itemView.findViewById(R.id.date);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            activityType = (TextView) itemView.findViewById(R.id.activityType);
            note = (TextView) itemView.findViewById(R.id.note);
            llComplete = (LinearLayout) itemView.findViewById(R.id.complete);
            llParent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
        }
    }
}
