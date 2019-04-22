package com.bookservice.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bookservice.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder> {

    private ArrayList<HashMap<String, String>> arrayList;
    private Activity activity;

    public CustomerDetailAdapter(ArrayList<HashMap<String, String>> details, Activity activity) {
        this.arrayList = details;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer_detail, viewGroup, false);
        return new CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mName.setText(arrayList.get(i).get("name"));
        itemViewHolder.mType.setText(arrayList.get(i).get("type"));
        itemViewHolder.mEmail.setText(arrayList.get(i).get("email"));
        itemViewHolder.mMobile.setText(arrayList.get(i).get("mobileNo"));
        itemViewHolder.mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomerDetailAdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mType;
        TextView mEmail;
        TextView mMobile;
        RadioButton mSelect;

        public CustomerDetailAdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mType = (TextView) itemView.findViewById(R.id.type);
            mEmail = (TextView) itemView.findViewById(R.id.email);
            mMobile = (TextView) itemView.findViewById(R.id.mobile);
            mSelect = (RadioButton) itemView.findViewById(R.id.radioButton);
        }
    }
}

