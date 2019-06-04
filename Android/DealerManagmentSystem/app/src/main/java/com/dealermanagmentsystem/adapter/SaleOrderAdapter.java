package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.saleorder.Record;

import java.util.List;

public class SaleOrderAdapter extends RecyclerView.Adapter<SaleOrderAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;

    public SaleOrderAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
    }

    @NonNull
    @Override
    public SaleOrderAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sale_order, viewGroup, false);
        return new SaleOrderAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SaleOrderAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mName.setText(mRecords.get(i).getName());
        final Object userId = mRecords.get(i).getUserId();
        if (userId instanceof List) {
            itemViewHolder.mUserName.setText(String.valueOf(((List) userId).get(1)));
        } else {
            itemViewHolder.mUserName.setVisibility(View.GONE);
        }

        itemViewHolder.mDateOrder.setText(mRecords.get(i).getDateOrder());
        itemViewHolder.mAmount.setText("\u20B9 " + mRecords.get(i).getAmountTotal());


    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mUserName;
        TextView mDateOrder;
        TextView mAmount;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mDateOrder = (TextView) itemView.findViewById(R.id.date_order);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
