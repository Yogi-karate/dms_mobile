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
import com.dealermanagmentsystem.ui.base.BaseApplication;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.tasks.TasksActivity;
import com.dealermanagmentsystem.ui.service.create.ServiceCreateBookingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.LEAD_EDIT_ENQUIRY;

public class ServiceLeadAdapter extends RecyclerView.Adapter<ServiceLeadAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;
    List<Record> mFilterRecords;

    public ServiceLeadAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
        this.mFilterRecords = new ArrayList<>();
        this.mFilterRecords.addAll(mRecords);
    }

    @NonNull
    @Override
    public ServiceLeadAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service_lead, viewGroup, false);
        return new ServiceLeadAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceLeadAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.llActions.setVisibility(View.GONE);
        itemViewHolder.mName.setText(mRecords.get(i).getName());
        itemViewHolder.mPartnerName.setText(mRecords.get(i).getPartnerName());
        itemViewHolder.mUserName.setText(mRecords.get(i).getActivityDateDeadLine());
        itemViewHolder.mTeamName.setText(mRecords.get(i).getMobile());

        final Object disposition = mRecords.get(i).getDisposition();
        if (disposition instanceof List) {
            itemViewHolder.mDisposition.setText(String.valueOf(((List) disposition).get(1)));
        }


        final String callState = mRecords.get(i).getCallState();
        if (callState.equalsIgnoreCase("fresh")) {
            itemViewHolder.mStatus.setText("Fresh");
          //  itemViewHolder.mStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.light_blue));
            itemViewHolder.llStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.light_blue));
        } else if (callState.equalsIgnoreCase("done")) {
            itemViewHolder.mStatus.setText("Completed");
          //  itemViewHolder.mStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.green));
            itemViewHolder.llStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.green));
        } else if (callState.equalsIgnoreCase("progress")) {
            itemViewHolder.mStatus.setText("In-Progress");
          //  itemViewHolder.mStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.yellow));
            itemViewHolder.llStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.yellow));
        } else if (callState.equalsIgnoreCase("call-back")) {
            itemViewHolder.mStatus.setText("Callback");
          //  itemViewHolder.mStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.red));
            itemViewHolder.llStatusColor.setBackgroundColor(activity.getResources().getColor(R.color.red));
        }
      /*  itemViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(activity, CreateEnquiryActivity.class);
                    intent.putExtra(EXTRA_ENQUIRY, LEAD_EDIT_ENQUIRY);
                    intent.putExtra(EXTRA_ENQUIRY_ID, String.valueOf(mRecords.get(i).getId()));
                    activity.startActivityForResult(intent, 2);
            }
        });*/

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
                intent.putExtra(EXTRA_ACTIVITY_COMING_FROM, "ServiceLeads");
                activity.startActivity(intent);
            }
        });

        itemViewHolder.llBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ServiceCreateBookingActivity.class);
                intent.putExtra(EXTRA_LEAD_ID, String.valueOf(mRecords.get(i).getId()));
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

        itemViewHolder.llLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.getEventBus().post(new LeadActionLostEvent(String.valueOf(mRecords.get(i).getId())));
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
        TextView mStatus;
        LinearLayout llParent;
        LinearLayout llCall;
        LinearLayout llLost;
        LinearLayout llTasks;
        LinearLayout llActions;
        LinearLayout llBook;
        TextView mTxtActions;
        TextView mDisposition;
        //TextView mStatusColor;
        LinearLayout llStatusColor;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPartnerName = (TextView) itemView.findViewById(R.id.partner_name);
            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mTeamName = (TextView) itemView.findViewById(R.id.team_name);
            mTxtActions = (TextView) itemView.findViewById(R.id.txt_actions);
            mStatus = (TextView) itemView.findViewById(R.id.status);
          //  mStatusColor = (TextView) itemView.findViewById(R.id.status_color);
            mDisposition = (TextView) itemView.findViewById(R.id.disposition);
            llParent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            llCall = (LinearLayout) itemView.findViewById(R.id.ll_call);
            llLost = (LinearLayout) itemView.findViewById(R.id.lost);
            llTasks = (LinearLayout) itemView.findViewById(R.id.tasks);
            llActions = (LinearLayout) itemView.findViewById(R.id.ll_actions);
            llBook = (LinearLayout) itemView.findViewById(R.id.book);
            llStatusColor = (LinearLayout) itemView.findViewById(R.id.status_color_ll);
        }
    }
}
