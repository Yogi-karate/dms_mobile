package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.enquiry.Record;
import com.dealermanagmentsystem.data.model.tasks.TasksResponse;
import com.dealermanagmentsystem.event.LeadActionMoveEvent;
import com.dealermanagmentsystem.event.TasksCompleteEvent;
import com.dealermanagmentsystem.ui.base.BaseApplication;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<TasksResponse> mRecords;

    public TasksAdapter(Activity activity, List<TasksResponse> records) {
        this.activity = activity;
        this.mRecords = records;
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

        final Object activityTypeId = mRecords.get(i).getActivityTypeId();
        if (activityTypeId instanceof List) {
            itemViewHolder.activityType.setText(String.valueOf(((List) activityTypeId).get(1)));
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
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mSummary;
        TextView mDate, activityType, userName;
        LinearLayout llComplete;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mSummary = (TextView) itemView.findViewById(R.id.summary);
            mDate = (TextView) itemView.findViewById(R.id.date);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            activityType = (TextView) itemView.findViewById(R.id.activityType);
            llComplete = (LinearLayout) itemView.findViewById(R.id.complete);
        }
    }
}
