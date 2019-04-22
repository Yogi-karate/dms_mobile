package com.bookservice.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookservice.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.bookservice.constants.Constants.EXTRA_DESCRIPTION;
import static com.bookservice.constants.Constants.EXTRA_NAME;


public class OfferDetailAdapter extends RecyclerView.Adapter<OfferDetailAdapter.OfferAdapterItemViewHolder> {

    private ArrayList<HashMap<String, String>> arrayList;

    public OfferDetailAdapter(ArrayList<HashMap<String, String>> details) {
        this.arrayList = details;
    }

    @NonNull
    @Override
    public OfferAdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_offer, viewGroup, false);
        return new OfferAdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OfferAdapterItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.mTitleText.setText(arrayList.get(i).get(EXTRA_NAME));
        itemViewHolder.mDescriptionText.setText(arrayList.get(i).get(EXTRA_DESCRIPTION));

        itemViewHolder.imgExpandCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemViewHolder.llExpandCollapse.getVisibility() == View.GONE) {
                    itemViewHolder.llExpandCollapse.setVisibility(View.VISIBLE);
                    itemViewHolder.imgExpandCollapse.setImageResource(R.drawable.ic_expand);
                } else {
                    itemViewHolder.llExpandCollapse.setVisibility(View.GONE);
                    itemViewHolder.imgExpandCollapse.setImageResource(R.drawable.ic_collapse);
                }
            }
        });

        itemViewHolder.llParentCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemViewHolder.llExpandCollapse.getVisibility() == View.GONE) {
                    itemViewHolder.llExpandCollapse.setVisibility(View.VISIBLE);
                    itemViewHolder.imgExpandCollapse.setImageResource(R.drawable.ic_expand);
                } else {
                    itemViewHolder.llExpandCollapse.setVisibility(View.GONE);
                    itemViewHolder.imgExpandCollapse.setImageResource(R.drawable.ic_collapse);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class OfferAdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleText;
        TextView mDescriptionText;
        LinearLayout llExpandCollapse;
        LinearLayout llParentCollapse;
        ImageView imgExpandCollapse;

        public OfferAdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleText = (TextView) itemView.findViewById(R.id.title);
            mDescriptionText = (TextView) itemView.findViewById(R.id.description);
            llExpandCollapse = (LinearLayout) itemView.findViewById(R.id.ll_expand_collapse);
            llParentCollapse = (LinearLayout) itemView.findViewById(R.id.ll_parent_collapse);
            imgExpandCollapse = (ImageView) itemView.findViewById(R.id.expand_collapse);
        }
    }
}
