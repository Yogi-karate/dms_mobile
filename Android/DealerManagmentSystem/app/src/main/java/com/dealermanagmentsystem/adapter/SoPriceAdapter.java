package com.dealermanagmentsystem.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.soprice.Record;

import java.util.List;

public class SoPriceAdapter extends RecyclerView.Adapter<SoPriceAdapter.AdapterItemViewHolder> {

    List<Record> mRecords;

    public SoPriceAdapter(List<Record> records) {
        this.mRecords = records;
    }

    @NonNull
    @Override
    public SoPriceAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_price, viewGroup, false);
        return new SoPriceAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SoPriceAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mProductName.setText(String.valueOf(mRecords.get(i).getName()));
        itemViewHolder.mSubTotal.setText(String.valueOf(mRecords.get(i).getPriceTotal())+" \u20B9" );
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mProductName, mSubTotal;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductName = (TextView) itemView.findViewById(R.id.product);
            mSubTotal = (TextView) itemView.findViewById(R.id.sub_total);
        }
    }
}
