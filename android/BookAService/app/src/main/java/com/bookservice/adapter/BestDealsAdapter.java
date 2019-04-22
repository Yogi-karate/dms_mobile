package com.bookservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bookservice.R;
import com.bookservice.data.model.offers.Offer;
import com.bookservice.ui.activity.offer.OfferDetailActivity;
import com.bookservice.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;

import static com.bookservice.constants.Constants.EXTRA_DESCRIPTION;
import static com.bookservice.constants.Constants.EXTRA_NAME;


public class BestDealsAdapter extends RecyclerView.Adapter<BestDealsAdapter.MyViewHolder> {

    List<Offer> data = new ArrayList<>();
    String mUrl;
    Context context;

    public BestDealsAdapter(List<Offer> data, String url, Context context) {
        this.data = data;
        this.context = context;
        this.mUrl = url;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_single_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ImageLoad.loadImage(mUrl + data.get(position).getUrl(), holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OfferDetailActivity.class);
                intent.putExtra(EXTRA_NAME, data.get(position).getName());
                intent.putExtra(EXTRA_DESCRIPTION, data.get(position).getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
