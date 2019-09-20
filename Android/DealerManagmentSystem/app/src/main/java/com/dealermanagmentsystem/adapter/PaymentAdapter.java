package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.payment.Record;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;

    public PaymentAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
    }

    @NonNull
    @Override
    public PaymentAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_payment, viewGroup, false);
        return new PaymentAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        final Object userId = mRecords.get(i).getPartnerId();
        if (userId instanceof List) {
            itemViewHolder.mName.setText(String.valueOf(((List) userId).get(1)));
        }
        itemViewHolder.mInvoiceNo.setText(mRecords.get(i).getName());
        itemViewHolder.mDate.setText(mRecords.get(i).getPaymentDate());
        itemViewHolder.mAmount.setText("\u20B9 " +String.valueOf(mRecords.get(i).getAmount()));
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mInvoiceNo;
        TextView mDate;
        TextView mAmount;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mInvoiceNo = (TextView) itemView.findViewById(R.id.invoice_no);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
