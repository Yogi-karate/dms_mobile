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

public class VehicleDetailAdapter extends RecyclerView.Adapter<VehicleDetailAdapter.VehicleDetailAdapterItemViewHolder> {

    private ArrayList<HashMap<String, String>> arrayList;
    private Activity activity;

    public VehicleDetailAdapter(ArrayList<HashMap<String, String>> details, Activity activity) {
        this.arrayList = details;
        this.activity = activity;
    }

    @NonNull
    @Override
    public VehicleDetailAdapter.VehicleDetailAdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vehicle_detail, viewGroup, false);
        return new VehicleDetailAdapter.VehicleDetailAdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VehicleDetailAdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mBrand.setText(arrayList.get(i).get("brand") + " " + arrayList.get(i).get("model"));
        itemViewHolder.mRegNo.setText(arrayList.get(i).get("regNo"));
        itemViewHolder.mYear.setText(arrayList.get(i).get("year"));
        itemViewHolder.mType.setText(arrayList.get(i).get("type"));
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

    public class VehicleDetailAdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mBrand;
        TextView mRegNo;
        TextView mYear;
        TextView mType;
        RadioButton mSelect;

        public VehicleDetailAdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mBrand = (TextView) itemView.findViewById(R.id.brand);
            mType = (TextView) itemView.findViewById(R.id.type);
            mRegNo = (TextView) itemView.findViewById(R.id.reg_no);
            mYear = (TextView) itemView.findViewById(R.id.year);
            mSelect = (RadioButton) itemView.findViewById(R.id.radioButton);
        }
    }
}