package com.bookservice.ui.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bookservice.R;
import com.bookservice.adapter.CustomerDetailAdapter;
import com.bookservice.ui.base.BsBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public  class UserDetailActivity extends BsBaseActivity {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fabAddUser;
    private GridLayoutManager gridLayoutManager;
    private CustomerDetailAdapter offerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        ButterKnife.bind(this);

        showTile("Customer Detail");
        showWhiteStatusBar(Color.WHITE);
        showBackButton();

        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerDetailsDialog();
            }
        });

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        offerAdapter = new CustomerDetailAdapter(getCustomerDetails(), this);
        recyclerView.setAdapter(offerAdapter);

    }

    public ArrayList<HashMap<String, String>> getCustomerDetails() {

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("name", "Nikhil Vivaan");
        hashMap1.put("email", "nikhil@gmail.com");
        hashMap1.put("mobileNo", "9739797397");
        hashMap1.put("type", "Self");
        arrayList.add(hashMap1);

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("name", "Karthik");
        hashMap2.put("email", "karthik@gmail.com");
        hashMap2.put("mobileNo", "9739797397");
        hashMap2.put("type", "Other");
        arrayList.add(hashMap2);

        HashMap<String, String> hashMap3 = new HashMap<>();
        hashMap3.put("name", "Ram");
        hashMap3.put("email", "ram@gmail.com");
        hashMap3.put("mobileNo", "9739797397");
        hashMap3.put("type", "Other");
        arrayList.add(hashMap3);

        HashMap<String, String> hashMap4 = new HashMap<>();
        hashMap4.put("name", "Yoga");
        hashMap4.put("email", "yoga@gmail.com");
        hashMap4.put("mobileNo", "9739797397");
        hashMap4.put("type", "Other");
        arrayList.add(hashMap4);

        HashMap<String, String> hashMap6 = new HashMap<>();
        hashMap6.put("name", "Ram");
        hashMap6.put("email", "ram@gmail.com");
        hashMap6.put("mobileNo", "9739797397");
        hashMap6.put("type", "Other");
        arrayList.add(hashMap6);


        HashMap<String, String> hashMap5 = new HashMap<>();
        hashMap5.put("name", "Yoga");
        hashMap5.put("email", "yoga@gmail.com");
        hashMap5.put("mobileNo", "9739797397");
        hashMap5.put("type", "Other");
        arrayList.add(hashMap5);

        return arrayList;
    }

    public void customerDetailsDialog() {
        View view = getLayoutInflater().inflate(R.layout.customer_bottom_sheet_dialog, null);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        final Button ok = view.findViewById(R.id.ok);
        final Button close = view.findViewById(R.id.close);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
