package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.delivery.Record;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;

    public DeliveryAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
    }

    @NonNull
    @Override
    public DeliveryAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_delivery, viewGroup, false);
        return new DeliveryAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeliveryAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mName.setText(mRecords.get(i).getName());
        itemViewHolder.mScheduledDate.setText(mRecords.get(i).getScheduledDate());
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mScheduledDate;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mScheduledDate = (TextView) itemView.findViewById(R.id.scheduled_date);
        }
    }
}
