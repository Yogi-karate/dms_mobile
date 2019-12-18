package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.common.Record;

import java.util.ArrayList;
import java.util.List;

public class SpinnerVehicleAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Record> list = new ArrayList<>();
    boolean mIsWithoutDisplayName;

    public SpinnerVehicleAdapter(Context applicationContext, List<Record> list) {
        this.context = applicationContext;
        this.list = list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public SpinnerVehicleAdapter(Context applicationContext, List<Record> priceListRecords, boolean isWithoutDisplayName) {
        this.context = applicationContext;
        this.list = priceListRecords;
        inflter = (LayoutInflater.from(applicationContext));
        mIsWithoutDisplayName = isWithoutDisplayName;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView txtNames = (TextView) view.findViewById(R.id.textView);
        if (mIsWithoutDisplayName) {
            txtNames.setText(list.get(i).getName());
        } else {
            txtNames.setText(list.get(i).getDisplay_name());
        }

        return view;
    }
}