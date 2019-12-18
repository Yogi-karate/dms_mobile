package com.dealermanagmentsystem.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.enquiry.Record;
import com.dealermanagmentsystem.event.LeadActionLostEvent;
import com.dealermanagmentsystem.event.LeadActionMoveEvent;
import com.dealermanagmentsystem.event.LeadActionWonEvent;
import com.dealermanagmentsystem.ui.base.BaseApplication;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.subenquirydetail.SubEnquiryDetailActivity;
import com.dealermanagmentsystem.ui.enquiry.tasks.TasksActivity;
import com.dealermanagmentsystem.ui.quotation.QuotationCreateActivity;
import com.dealermanagmentsystem.ui.saleorder.SaleOrderActivity;
import com.dealermanagmentsystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dealermanagmentsystem.constants.Constants.EDIT_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_EMAIL;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FOLLOWUP_DATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_MOBILE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_PARTNER_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_QUOTATION_COUNT;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_NAME;
import static com.dealermanagmentsystem.constants.Constants.LEAD_EDIT_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.QUOTATION;

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
        itemViewHolder.quotationCount.setText(String.valueOf(mRecords.get(i).getSaleNumber()));
        itemViewHolder.quotationCount.setPaintFlags(itemViewHolder.quotationCount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        itemViewHolder.quotationCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SaleOrderActivity.class);
                intent.putExtra(EXTRA_SALE_TYPE, QUOTATION);
                intent.putExtra(EXTRA_SALE_TYPE_ID, "draft");
                intent.putExtra(EXTRA_LEAD_ID, String.valueOf(mRecords.get(i).getId()));
                activity.startActivity(intent);
            }
        });


        itemViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SubEnquiryDetailActivity.class);
                intent.putExtra(EXTRA_ENQUIRY_ID, mRecords.get(i).getId());
                activity.startActivity(intent);

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
                String strMobile;
                strMobile = mRecords.get(i).getMobile();
                Utils.callMobile(strMobile, activity);
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
        TextView quotationCount;

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
            quotationCount = (TextView) itemView.findViewById(R.id.quotation_count);

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
