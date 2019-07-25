package com.dealermanagmentsystem.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.teamdetail.Result;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.lead.LeadActivity;
import com.dealermanagmentsystem.ui.home.HomeActivity;
import com.dealermanagmentsystem.ui.userenquiry.UserMonthDetailsActivity;

import java.util.List;

import static com.dealermanagmentsystem.constants.Constants.EDIT_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_STAGE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_STATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_NAME;
import static com.dealermanagmentsystem.constants.Constants.STAGE_WARM;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.AdapterItemViewHolder> {

    List<Result> mRecords;
    Activity mActivity;

    public TeamAdapter(List<Result> records, Activity activity) {
        this.mRecords = records;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public TeamAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_teams, viewGroup, false);
        return new TeamAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeamAdapter.AdapterItemViewHolder itemViewHolder, final int i) {

        itemViewHolder.mCount.setText(String.valueOf(mRecords.get(i).getUserIdCount()));
        itemViewHolder.mBooked.setText(String.valueOf(mRecords.get(i).getUserBookedId()));

        itemViewHolder.mCount.setPaintFlags(itemViewHolder.mCount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        itemViewHolder.mBooked.setPaintFlags(itemViewHolder.mBooked.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        String id = "";
        String name = "";
        final Object userId = mRecords.get(i).getUserId();
        if (userId instanceof List) {
            name = String.valueOf(((List) userId).get(1));
            id = String.valueOf(((List) userId).get(0));
            itemViewHolder.mName.setText(name);
        }

        final String finalId = id;
        final String finalName = name;
        itemViewHolder.mCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, UserMonthDetailsActivity.class);
                intent.putExtra(EXTRA_USER_ID, finalId);
                intent.putExtra(EXTRA_USER_NAME, finalName);
                mActivity.startActivity(intent);
            }
        });

        itemViewHolder.mBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, LeadActivity.class);
                intent.putExtra(EXTRA_USER_ID, finalId);
                intent.putExtra(EXTRA_USER_NAME, finalName);
                intent.putExtra(EXTRA_FROM, "team");
                mActivity.startActivity(intent);
            }
        });
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
