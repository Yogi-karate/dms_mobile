package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.userdetail.UserDetailResponse;

import java.util.List;

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<UserDetailResponse> mRecords;

    public UserDetailAdapter(Activity activity, List<UserDetailResponse> records) {
        this.activity = activity;
        this.mRecords = records;
    }

    @NonNull
    @Override
    public UserDetailAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_detail, viewGroup, false);
        return new UserDetailAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserDetailAdapter.AdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mDate.setText(mRecords.get(i).getDate());
        itemViewHolder.mCount.setText(String.valueOf(mRecords.get(i).getCount()));

    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mDate, mCount;
        LinearLayout llParent;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.txt_date);
            mCount = (TextView) itemView.findViewById(R.id.txt_count);
            llParent = (LinearLayout) itemView.findViewById(R.id.ll_parent);

        }
    }
}
