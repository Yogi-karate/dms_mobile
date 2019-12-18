package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.saleorder.Record;
import com.dealermanagmentsystem.ui.saleorder.SaleOrderEditActivity;
import com.dealermanagmentsystem.ui.saleorder.detail.SaleOrderDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALES_CONSULTANT_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_STATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_NAME;

public class SaleOrderAdapter extends RecyclerView.Adapter<SaleOrderAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<Record> mRecords;
    List<Record> mFilterRecords;
    String mStrSaleTypeID;

    public SaleOrderAdapter(Activity activity, List<Record> records, String strSaleTypeID) {
        this.activity = activity;
        this.mRecords = records;
        this.mFilterRecords = new ArrayList<>();
        this.mFilterRecords.addAll(mRecords);
        this.mStrSaleTypeID = strSaleTypeID;
    }

    @NonNull
    @Override
    public SaleOrderAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sale_order, viewGroup, false);
        return new SaleOrderAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SaleOrderAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        String strTeamName = "";
        String strSalesConsultantName = "";
        itemViewHolder.mOrderNo.setText(mRecords.get(i).getName());

        final Object partnerId = mRecords.get(i).getPartnerId();
        if (partnerId instanceof List) {
            itemViewHolder.mCustomerName.setText(String.valueOf(((List) partnerId).get(1)));
        }

        itemViewHolder.mState.setText(mRecords.get(i).getState());

        final Object userId = mRecords.get(i).getUserId();
        if (userId instanceof List) {
            strSalesConsultantName = String.valueOf(((List) userId).get(1));
        }

        final Object teamID = mRecords.get(i).getTeamId();
        if (teamID instanceof List) {
            strTeamName = String.valueOf(((List) teamID).get(1));
        }

        if (mStrSaleTypeID.equalsIgnoreCase("Booked")) {
            itemViewHolder.llDob.setVisibility(View.VISIBLE);
            itemViewHolder.llBookingAmount.setVisibility(View.VISIBLE);

            itemViewHolder.mDob.setText(mRecords.get(i).getDob());
            itemViewHolder.mBookingAmount.setText("\u20B9 " + String.valueOf(mRecords.get(i).getBookingAmt()));
        } else {
            itemViewHolder.llSalesConsultant.setVisibility(View.VISIBLE);
            itemViewHolder.llTeamName.setVisibility(View.VISIBLE);

            itemViewHolder.mSalesConsultantName.setText(strSalesConsultantName);
            itemViewHolder.mTeamName.setText(strTeamName);
        }


        final String finalStrSalesConsultantName = strSalesConsultantName;
        final String finalStrTeamName = strTeamName;
        itemViewHolder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (mRecords.get(i).getState().equalsIgnoreCase("draft")) {
                    Intent intent = new Intent(activity, SaleOrderDetailActivity.class);
                    intent.putExtra(EXTRA_SALE_ORDER_ID, String.valueOf(mRecords.get(i).getId()));
                    intent.putExtra(EXTRA_TEAM_NAME, finalStrTeamName);
                    intent.putExtra(EXTRA_SALES_CONSULTANT_NAME, finalStrSalesConsultantName);
                    intent.putExtra(EXTRA_SALE_ORDER_NAME, mRecords.get(i).getName());
                    intent.putExtra(EXTRA_SALE_ORDER_STATE, mRecords.get(i).getState());
                    activity.startActivity(intent);
                //}

            }
        });
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

        TextView mOrderNo;
        TextView mDob;
        TextView mStockStatus;
        TextView mBookingAmount;
        TextView mCustomerName;
        TextView mSalesConsultantName;
        TextView mTeamName;
        TextView mState;

        LinearLayout llParent;
        LinearLayout llDob;
        LinearLayout llSalesConsultant;
        LinearLayout llTeamName;
        LinearLayout llBookingAmount;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mOrderNo = (TextView) itemView.findViewById(R.id.order_no);
            mDob = (TextView) itemView.findViewById(R.id.dob);
            mStockStatus = (TextView) itemView.findViewById(R.id.stock_status);
            mBookingAmount = (TextView) itemView.findViewById(R.id.booking_amount);
            mCustomerName = (TextView) itemView.findViewById(R.id.customer_name);
            mSalesConsultantName = (TextView) itemView.findViewById(R.id.sales_consultant);
            mTeamName = (TextView) itemView.findViewById(R.id.team_name);
            mState = (TextView) itemView.findViewById(R.id.state);

            llParent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            llDob = (LinearLayout) itemView.findViewById(R.id.ll_dob);
            llSalesConsultant = (LinearLayout) itemView.findViewById(R.id.ll_sc);
            llTeamName = (LinearLayout) itemView.findViewById(R.id.ll_team_name);
            llBookingAmount = (LinearLayout) itemView.findViewById(R.id.ll_booking_amount);
        }
    }
}
