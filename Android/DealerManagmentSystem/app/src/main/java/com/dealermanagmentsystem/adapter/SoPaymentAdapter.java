package com.dealermanagmentsystem.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.sopayment.Record;

import java.util.List;

public class SoPaymentAdapter extends RecyclerView.Adapter<SoPaymentAdapter.AdapterItemViewHolder> {

    List<Record> mRecords;

    public SoPaymentAdapter(List<Record> records) {
        this.mRecords = records;
    }


    @NonNull
    @Override
    public SoPaymentAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_so_payment, viewGroup, false);
        return new SoPaymentAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SoPaymentAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mDate.setText(mRecords.get(i).getPaymentDate());
        itemViewHolder.mType.setText(mRecords.get(i).getPaymentType());
        itemViewHolder.mMemo.setText(mRecords.get(i).getCommunication());
        itemViewHolder.mAmount.setText(String.valueOf(mRecords.get(i).getAmount())+" \u20B9" );
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mDate,mType, mMemo,mAmount;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mType = (TextView) itemView.findViewById(R.id.type);
            mMemo = (TextView) itemView.findViewById(R.id.memo);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
