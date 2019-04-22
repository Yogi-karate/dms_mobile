package com.bookservice.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bookservice.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class ImageLoad {

    public static void loadImage(final String imageUrl,
                                 final ImageView imageView) {
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.h4)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);
    }


}
