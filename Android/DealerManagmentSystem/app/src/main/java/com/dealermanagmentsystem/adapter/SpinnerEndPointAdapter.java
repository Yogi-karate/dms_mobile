package com.dealermanagmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.endpoint.Body;

import java.util.ArrayList;
import java.util.List;

public class SpinnerEndPointAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Body> list = new ArrayList<>();

    public SpinnerEndPointAdapter(Context applicationContext, List<Body> list) {
        this.context = applicationContext;
        this.list = list;
        inflter = (LayoutInflater.from(applicationContext));
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
        txtNames.setText(list.get(i).getName());
        return view;
    }
}