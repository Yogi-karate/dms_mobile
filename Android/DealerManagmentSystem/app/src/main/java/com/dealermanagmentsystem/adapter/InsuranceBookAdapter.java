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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.bookinginsurance.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class InsuranceBookAdapter extends RecyclerView.Adapter<InsuranceBookAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;
    List<Record> mFilterRecords;

    public InsuranceBookAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
        this.mFilterRecords = new ArrayList<>();
        this.mFilterRecords.addAll(mRecords);
    }

    @NonNull
    @Override
    public InsuranceBookAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_insurance_booking, viewGroup, false);
        return new InsuranceBookAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InsuranceBookAdapter.AdapterItemViewHolder itemViewHolder, final int i) {

        itemViewHolder.mCustomerName.setText(mRecords.get(i).getPartnerName());
        itemViewHolder.mMobile.setText(mRecords.get(i).getMobile());
        itemViewHolder.mIDV.setText(mRecords.get(i).getIdv());
        itemViewHolder.mBookingType.setText(mRecords.get(i).getBookingType());
        itemViewHolder.mNCB.setText(mRecords.get(i).getCurNcb());
        itemViewHolder.mNilDip.setText(mRecords.get(i).getCurDipOrComp());
        itemViewHolder.mPolicyNo.setText(mRecords.get(i).getPolicyNo());
        itemViewHolder.mPolicyAmount.setText("\u20B9 " + mRecords.get(i).getCurFinalPremium());

        final Object userId = mRecords.get(i).getRolloverCompany();
        if (userId instanceof List) {
            itemViewHolder.mInsuranceCompany.setText(String.valueOf(((List) userId).get(1)));
        }

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
                if (mFilterRecords.get(i).getPartnerName()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText) || mFilterRecords.get(i).getMobile()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mRecords.add(mFilterRecords.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mCustomerName;
        TextView mMobile;
        TextView mBookingType;
        LinearLayout llCall;

        TextView mIDV;
        TextView mNCB;
        TextView mInsuranceCompany;

        TextView mNilDip;
        TextView mPolicyNo;
        TextView mPolicyAmount;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mCustomerName = (TextView) itemView.findViewById(R.id.customer_name);
            mNCB = (TextView) itemView.findViewById(R.id.ncb);
            mIDV = (TextView) itemView.findViewById(R.id.idv);
            mMobile = (TextView) itemView.findViewById(R.id.mobile);
            llCall = (LinearLayout) itemView.findViewById(R.id.ll_call);
            mInsuranceCompany = (TextView) itemView.findViewById(R.id.insurance_company);
            mNilDip = (TextView) itemView.findViewById(R.id.nil_dip);
            mPolicyNo = (TextView) itemView.findViewById(R.id.policy_no);
            mBookingType = (TextView) itemView.findViewById(R.id.booking_type);
            mPolicyAmount = (TextView) itemView.findViewById(R.id.policy_amount);
        }
    }
}
