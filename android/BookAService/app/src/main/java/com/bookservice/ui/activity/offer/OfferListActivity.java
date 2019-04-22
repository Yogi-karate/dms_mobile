package com.bookservice.ui.activity.offer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bookservice.R;
import com.bookservice.ui.base.BsBaseActivity;

import butterknife.ButterKnife;

public class OfferListActivity extends BsBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);
        ButterKnife.bind(this);
        showTile("General Service");
        showWhiteStatusBar(Color.WHITE);
        showBackButton();
    }
}
