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
import com.dealermanagmentsystem.data.model.bookingservice.Record;
import com.dealermanagmentsystem.ui.service.booking.ServiceBookingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ServiceBookAdapter extends RecyclerView.Adapter<ServiceBookAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;
    List<Record> mFilterRecords;

   /* public ServiceBookAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
        this.mFilterRecords = new ArrayList<>();
        this.mFilterRecords.addAll(mRecords);
    }*/

    public ServiceBookAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
        this.mFilterRecords = new ArrayList<>();
        this.mFilterRecords.addAll(mRecords);
    }

    @NonNull
    @Override
    public ServiceBookAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service_booking, viewGroup, false);
        return new ServiceBookAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceBookAdapter.AdapterItemViewHolder itemViewHolder, final int i) {

        itemViewHolder.mCustomerName.setText(mRecords.get(i).getPartnerName());
        itemViewHolder.mMobile.setText(mRecords.get(i).getMobile());
        itemViewHolder.mModel.setText(mRecords.get(i).getVehicleModel());
        itemViewHolder.mBookingType.setText(mRecords.get(i).getBookingType());
        itemViewHolder.mServiceType.setText(mRecords.get(i).getServiceType());
        itemViewHolder.mDatePickUp.setText(mRecords.get(i).getDop());

        final Object userId = mRecords.get(i).getUserId();
        if (userId instanceof List) {
            itemViewHolder.mTelleCallerName.setText(String.valueOf(((List) userId).get(1)));
        }

        final Object locationId = mRecords.get(i).getLocationId();
        if (locationId instanceof List) {
            itemViewHolder.mLocationOfService.setText(String.valueOf(((List) locationId).get(1)));
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
        TextView mTelleCallerName;
        TextView mMobile;
        TextView mModel;
        TextView mBookingType;
        TextView mDatePickUp;
        TextView mServiceType;
        TextView mLocationOfService;
        LinearLayout llCall;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mCustomerName = (TextView) itemView.findViewById(R.id.customer_name);
            mModel = (TextView) itemView.findViewById(R.id.vehicle_model);
            mTelleCallerName = (TextView) itemView.findViewById(R.id.telecaller_name);
            mMobile = (TextView) itemView.findViewById(R.id.mobile);
            llCall = (LinearLayout) itemView.findViewById(R.id.ll_call);
            mDatePickUp = (TextView) itemView.findViewById(R.id.date_pick_up);
            mServiceType = (TextView) itemView.findViewById(R.id.service_type);
            mLocationOfService = (TextView) itemView.findViewById(R.id.location);
            mBookingType = (TextView) itemView.findViewById(R.id.booking_type);
        }
    }
}
