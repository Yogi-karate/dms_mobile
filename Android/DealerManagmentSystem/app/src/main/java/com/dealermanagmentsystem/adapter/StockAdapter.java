package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.stock.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;
    List<Record> mFilterRecords;

    public StockAdapter(Activity activity, List<Record> records) {
        this.activity = activity;
        this.mRecords = records;
        this.mFilterRecords = new ArrayList<>();
        this.mFilterRecords.addAll(mRecords);
    }

    @NonNull
    @Override
    public StockAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_stock_list, viewGroup, false);
        return new StockAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mModel.setText(mRecords.get(i).getModel());
        itemViewHolder.mVariant.setText(mRecords.get(i).getVariant());
        itemViewHolder.mColor.setText(mRecords.get(i).getColor());

        final Object locationId = mRecords.get(i).getLocationId();
        if (locationId instanceof List) {
            itemViewHolder.mLocation.setText(String.valueOf(((List) locationId).get(1)));
        }

        itemViewHolder.mEngineNo.setText(mRecords.get(i).getName());
        itemViewHolder.mAge.setText(String.valueOf(mRecords.get(i).getAge()));
        itemViewHolder.mStatus.setText(mRecords.get(i).getState());
        itemViewHolder.mAllotted.setText(mRecords.get(i).getAllocationState());
        itemViewHolder.mCustomerName.setText(mRecords.get(i).getPartnerName());

      /*  itemViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SaleOrderEditActivity.class);
                intent.putExtra(EXTRA_BOOKING_ID, String.valueOf(mRecords.get(i).getId()));
                intent.putExtra(EXTRA_PAYMENT_DATE, mRecords.get(i).getPaymentDate());
                intent.putExtra(EXTRA_DELIVERY_DATE, mRecords.get(i).getDeliveryDate());
                intent.putExtra(EXTRA_FINANCE_TYPE, mRecords.get(i).getFinanceType());
                final Object financierName = mRecords.get(i).getFinancierName();
                if (financierName instanceof List) {
                    intent.putExtra(EXTRA_FINANCIER, String.valueOf(((List) financierName).get(0)));
                } else {
                    intent.putExtra(EXTRA_FINANCIER, "");
                }


                activity.startActivity(intent);
            }
        });*/
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mRecords.clear();
        if (charText.length() == 0) {
            mRecords.addAll(mFilterRecords);
        } else {
            for (int i = 0; i < mFilterRecords.size(); i++) {
                String strLocationName = "";

                final Object locationId = mFilterRecords.get(i).getLocationId();
                if (locationId instanceof List) {
                   strLocationName =  String.valueOf(((List) locationId).get(1));
                }

                if (mFilterRecords.get(i).getName()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText) || mFilterRecords.get(i).getModel()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText)|| mFilterRecords.get(i).getVariant()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText) || strLocationName
                        .toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mRecords.add(mFilterRecords.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {


        TextView mModel;
        TextView mVariant;
        TextView mColor;
        TextView mEngineNo;
        TextView mLocation;
        TextView mCustomerName;
        TextView mAllotted;
        TextView mStatus;
        TextView mAge;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mModel = (TextView) itemView.findViewById(R.id.model);
            mVariant = (TextView) itemView.findViewById(R.id.variant);
            mColor = (TextView) itemView.findViewById(R.id.color);
            mEngineNo = (TextView) itemView.findViewById(R.id.engine_number);
            mLocation = (TextView) itemView.findViewById(R.id.location);
            mCustomerName = (TextView) itemView.findViewById(R.id.customer_name);
            mAllotted = (TextView) itemView.findViewById(R.id.allotted);
            mStatus = (TextView) itemView.findViewById(R.id.status);
            mAge = (TextView) itemView.findViewById(R.id.age);
        }
    }
}
