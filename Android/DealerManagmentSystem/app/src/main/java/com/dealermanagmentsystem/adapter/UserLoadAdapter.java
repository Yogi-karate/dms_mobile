package com.dealermanagmentsystem.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.loadusers.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UserLoadAdapter extends RecyclerView.Adapter<UserLoadAdapter.AdapterItemViewHolder> {

    private Activity activity;
    List<User> mUsers;
    String stringExtra;
    List<User> mFilterUsers;

    public UserLoadAdapter(Activity activity, List<User> users) {
        this.activity = activity;
        this.mUsers = users;
        this.mFilterUsers = new ArrayList<>();
        this.mFilterUsers.addAll(mUsers);
        this.stringExtra = stringExtra;
    }

    @NonNull
    @Override
    public UserLoadAdapter.AdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_load_user, viewGroup, false);
        return new UserLoadAdapter.AdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserLoadAdapter.AdapterItemViewHolder itemViewHolder, final int i) {

        itemViewHolder.mUserName.setText(mUsers.get(i).getName());
        itemViewHolder.mNumber.setText(mUsers.get(i).getMobile());
        itemViewHolder.mEmail.setText(mUsers.get(i).getEmail());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mUsers.clear();
        if (charText.length() == 0) {
            mUsers.addAll(mFilterUsers);
        } else {
            for (int i = 0; i < mFilterUsers.size(); i++) {
                if (mFilterUsers.get(i).getName()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText) || mFilterUsers.get(i).getMobile()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText) || mFilterUsers.get(i).getEmail()
                        .toLowerCase(Locale.getDefault())
                        .contains(charText) ) {
                    mUsers.add(mFilterUsers.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


    public class AdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mUserName;
        TextView mNumber;
        TextView mEmail;

        public AdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mNumber = (TextView) itemView.findViewById(R.id.user_number);
            mEmail = (TextView) itemView.findViewById(R.id.user_email);
        }
    }
}

