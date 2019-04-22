package com.bookservice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.data.model.category.Category;
import com.bookservice.data.pojos.SingleVertical;
import com.bookservice.ui.activity.offer.OfferListActivity;
import com.bookservice.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> data = new ArrayList<>();
    Activity mActivity;

    public CategoryAdapter(Activity activity, List<Category> data) {
        this.data = data;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_single_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageLoad.loadImage("" + data.get(position).getUrl(), holder.image);
        holder.txtTitle.setText(data.get(position).getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, OfferListActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
