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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.enquiry.Record;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;

import java.util.List;

import static com.dealermanagmentsystem.constants.Constants.EDIT_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;

public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;
    String stringExtra;

    public EnquiryAdapter(Activity activity, List<Record> records, String stringExtra) {
        this.activity = activity;
        this.mRecords = records;
        this.stringExtra = stringExtra;
    }

    @NonNull
    @Override
    public EnquiryAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_enquiry, viewGroup, false);
        return new EnquiryAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EnquiryAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        String name = mRecords.get(i).getName();

        itemViewHolder.mPartnerName.setText(mRecords.get(i).getPartnerName());

        if (stringExtra.equalsIgnoreCase(ENQUIRY)) {
            if (!TextUtils.isEmpty(name)) {
                name = name.substring(0, name.indexOf('-'));
                itemViewHolder.mName.setText(name);

            }
            itemViewHolder.mUserName.setText(mRecords.get(i).getDateFollowUp());
            itemViewHolder.mTeamName.setText(mRecords.get(i).getPartnerMobile());
            itemViewHolder.mEnquiryId.setText(String.valueOf(mRecords.get(i).getId()));
        } else {
            itemViewHolder.llEnquiryId.setVisibility(View.GONE);
            itemViewHolder.mName.setText(name);
            itemViewHolder.mUserName.setText(mRecords.get(i).getDateDeadLine());
            itemViewHolder.mTeamName.setText(mRecords.get(i).getMobile());
        }

       /* final Object userId = mRecords.get(i).getUserId();
        if (userId instanceof List) {
            itemViewHolder.mUserName.setText(String.valueOf(((List) userId).get(1)));
        } else {
            itemViewHolder.mUserName.setVisibility(View.GONE);
        }
*/

     /*   final Object teamId = mRecords.get(i).getTeamId();
        if (teamId instanceof List) {
            itemViewHolder.mTeamName.setText(String.valueOf(((List) teamId).get(1)));
        } else {
            itemViewHolder.mTeamName.setVisibility(View.GONE);
        }
        */

        if (stringExtra.equalsIgnoreCase(ENQUIRY)) {
            itemViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, CreateEnquiryActivity.class);
                    intent.putExtra(EXTRA_ENQUIRY, EDIT_ENQUIRY);
                    intent.putExtra(EXTRA_ENQUIRY_ID, String.valueOf(mRecords.get(i).getId()));
                    //  activity.startActivity(intent);
                    activity.startActivityForResult(intent, 2);
                }
            });
        }

        itemViewHolder.llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number;
                if (stringExtra.equalsIgnoreCase(ENQUIRY)) {
                    number = mRecords.get(i).getPartnerMobile();
                } else {
                    number = mRecords.get(i).getMobile();
                }


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
              /*  if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                activity.startActivity(callIntent);
*/

                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    activity.startActivity(callIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mPartnerName;
        TextView mUserName;
        TextView mTeamName;
        TextView mEnquiryId;
        LinearLayout llParent;
        LinearLayout llCall;
        LinearLayout llEnquiryId;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPartnerName = (TextView) itemView.findViewById(R.id.partner_name);
            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mTeamName = (TextView) itemView.findViewById(R.id.team_name);
            mEnquiryId = (TextView) itemView.findViewById(R.id.enquiry_id);
            llParent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            llCall = (LinearLayout) itemView.findViewById(R.id.ll_call);
            llEnquiryId = (LinearLayout) itemView.findViewById(R.id.ll_enquiry_id);
        }
    }
}

