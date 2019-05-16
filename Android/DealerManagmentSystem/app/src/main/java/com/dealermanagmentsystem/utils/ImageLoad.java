package com.dealermanagmentsystem.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.dealermanagmentsystem.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.dealermanagmentsystem.R.*;


public class ImageLoad {

    public static void loadImage(final String imageUrl,
                                 final ImageView imageView) {
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(drawable.h5)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);
    }


    public static void loadImageDrawable(int image, ImageView imageView) {
        Picasso.get()
                .load(image)
                .fit()

                .placeholder(drawable.h5)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);
    }

    public static void loadImageBase64(final String str,
                                       final ImageView imageView) {
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(drawable.example_avatar);
        } else {
            byte[] imageAsBytes = Base64.decode(str.getBytes(), Base64.DEFAULT);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        }

        /*Picasso.get()
                .load(String.valueOf(imageAsBytes))
                .fit()
                .centerCrop()
                .placeholder(drawable.example_avatar)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);*/

    }


}
