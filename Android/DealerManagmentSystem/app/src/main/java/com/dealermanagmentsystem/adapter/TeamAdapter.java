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
import com.dealermanagmentsystem.data.model.teamdetail.Result;
import com.dealermanagmentsystem.event.TasksCompleteEvent;
import com.dealermanagmentsystem.ui.base.BaseApplication;
import com.dealermanagmentsystem.ui.enquiry.tasks.TaskCreateActivity;

import java.util.List;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_DATE_DEADLINE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_SUMMARY;
import static com.dealermanagmentsystem.constants.Constants.KEY_EDIT_ACTIVITY;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Result> mRecords;

    public TeamAdapter(Activity activity, List<Result> records) {
        this.activity = activity;
        this.mRecords = records;
    }

    @NonNull
    @Override
    public TeamAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_teams, viewGroup, false);
        return new TeamAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeamAdapter.AdapterItemViewHolder itemViewHolder, final int i) {

        itemViewHolder.mCount.setText(mRecords.get(i).getUserIdCount());
        itemViewHolder.mBooked.setText(mRecords.get(i).getUserBookedId());

        final Object userId = mRecords.get(i).getUserId();
        if (userId instanceof List) {
            itemViewHolder.mName.setText(String.valueOf(((List) userId).get(1)));
        } else {
            itemViewHolder.mName.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mCount, mBooked;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.txt_name);
            mCount = (TextView) itemView.findViewById(R.id.txt_count);
            mBooked = (TextView) itemView.findViewById(R.id.txt_booked);
        }
    }
}
