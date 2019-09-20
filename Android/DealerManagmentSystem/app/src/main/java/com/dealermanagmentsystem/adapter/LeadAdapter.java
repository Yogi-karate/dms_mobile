package com.dealermanagmentsystem.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.enquiry.Record;
import com.dealermanagmentsystem.event.LeadActionLostEvent;
import com.dealermanagmentsystem.event.LeadActionMoveEvent;
import com.dealermanagmentsystem.event.LeadActionWonEvent;
import com.dealermanagmentsystem.ui.base.BaseApplication;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.tasks.TasksActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dealermanagmentsystem.constants.Constants.EDIT_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.LEAD_EDIT_ENQUIRY;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;
    String mStage;
    List<Record> mFilterRecords;

    public LeadAdapter(Activity activity, List<Record> records, String strStage) {
        this.activity = activity;
        this.mRecords = records;
        this.mFilterRecords = new ArrayList<>();
        this.mFilterRecords.addAll(mRecords);
        this.mStage = strStage;
    }

    @NonNull
    @Override
    public LeadAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lead, viewGroup, false);
        return new LeadAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeadAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mName.setText(mRecords.get(i).getName());
        itemViewHolder.mPartnerName.setText(mRecords.get(i).getPartnerName());
        itemViewHolder.mUserName.setText(mRecords.get(i).getActivityDateDeadLine());
        itemViewHolder.mTeamName.setText(mRecords.get(i).getMobile());

        itemViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CreateEnquiryActivity.class);
                intent.putExtra(EXTRA_ENQUIRY, LEAD_EDIT_ENQUIRY);
                intent.putExtra(EXTRA_ENQUIRY_ID, String.valueOf(mRecords.get(i).getId()));
                activity.startActivityForResult(intent, 2);
            }
        });

        itemViewHolder.llTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Object userId = mRecords.get(i).getUserId();
                int strUserId = 0;
                Double d;
                if (userId instanceof List) {
                    d = (Double) ((List) userId).get(0);
                    strUserId = d.intValue();
                }
                Intent intent = new Intent(activity, TasksActivity.class);
                intent.putExtra(EXTRA_LEAD_ID, String.valueOf(mRecords.get(i).getId()));
                intent.putExtra(EXTRA_USER_ID, strUserId);
                intent.putExtra(EXTRA_ACTIVITY_COMING_FROM, "Leads");
                activity.startActivity(intent);
            }
        });

        itemViewHolder.llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number;
                number = mRecords.get(i).getMobile();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    activity.startActivity(callIntent);
                }
            }
        });

        if (mStage.equalsIgnoreCase("booked")) {
            itemViewHolder.llWon.setVisibility(View.GONE);
        }

        itemViewHolder.llWon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.getEventBus().post(new LeadActionWonEvent(String.valueOf(mRecords.get(i).getId())));
            }
        });

        itemViewHolder.llLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.getEventBus().post(new LeadActionLostEvent(String.valueOf(mRecords.get(i).getId())));
            }
        });

        itemViewHolder.llMoveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.getEventBus().post(new LeadActionMoveEvent(String.valueOf(mRecords.get(i).getId())));
            }
        });

        itemViewHolder.mTxtActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemViewHolder.llActions.getVisibility() == View.GONE) {
                    itemViewHolder.llActions.setVisibility(View.VISIBLE);
                    itemViewHolder.llActions.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.left_to_right));
                } else {
                    itemViewHolder.llActions.setVisibility(View.GONE);
                    itemViewHolder.llActions.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.left_to_right));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mRecords.clear();
        if (charText.length() == 0) {
            mRecords.addAll(mFilterRecords);
        } else {
            for (int i = 0; i < mFilterRecords.size(); i++) {
                if (mFilterRecords.get(i).getName()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText) || mFilterRecords.get(i).getPartnerName()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mRecords.add(mFilterRecords.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mPartnerName;
        TextView mUserName;
        TextView mTeamName;
        LinearLayout llParent;
        LinearLayout llCall;
        LinearLayout llWon;
        LinearLayout llLost;
        LinearLayout llTasks;
        LinearLayout llMoveTo;
        TextView mTxtActions;
        LinearLayout llActions;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPartnerName = (TextView) itemView.findViewById(R.id.partner_name);
            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mTeamName = (TextView) itemView.findViewById(R.id.team_name);
            llParent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            llCall = (LinearLayout) itemView.findViewById(R.id.ll_call);
            llWon = (LinearLayout) itemView.findViewById(R.id.won);
            llLost = (LinearLayout) itemView.findViewById(R.id.lost);
            llTasks = (LinearLayout) itemView.findViewById(R.id.tasks);
            llMoveTo = (LinearLayout) itemView.findViewById(R.id.move);
            llActions = (LinearLayout) itemView.findViewById(R.id.ll_actions);
            mTxtActions = (TextView) itemView.findViewById(R.id.txt_actions);
        }
    }
}
