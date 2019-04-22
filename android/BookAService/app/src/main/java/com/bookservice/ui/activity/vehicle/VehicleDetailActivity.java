package com.bookservice.ui.activity.vehicle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bookservice.R;
import com.bookservice.adapter.VehicleDetailAdapter;
import com.bookservice.ui.base.BsBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleDetailActivity extends BsBaseActivity {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fabAddVehicle;
    private GridLayoutManager gridLayoutManager;
    private VehicleDetailAdapter offerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);
        ButterKnife.bind(this);

        showTile("Vehicle Detail");
        showWhiteStatusBar(Color.WHITE);
        showBackButton();

        fabAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleDetailsDialog();
            }
        });

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        offerAdapter = new VehicleDetailAdapter(getVehicleDetails(), this);
        recyclerView.setAdapter(offerAdapter);

    }

    public ArrayList<HashMap<String, String>> getVehicleDetails() {

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("brand", "Hyundai");
        hashMap1.put("model", "i20");
        hashMap1.put("regNo", "kA 05 MA 1234");
        hashMap1.put("year", "2016");
        hashMap1.put("type", "Self");
        arrayList.add(hashMap1);

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("brand", "Hyundai");
        hashMap2.put("model", "Creta");
        hashMap2.put("regNo", "KA 05 MA 1234");
        hashMap2.put("year", "2017");
        hashMap2.put("type", "Other");
        arrayList.add(hashMap2);

        HashMap<String, String> hashMap3 = new HashMap<>();
        hashMap3.put("brand", "Hyundai");
        hashMap3.put("model", "Tucson");
        hashMap3.put("regNo", "KA 05 MA 1234");
        hashMap3.put("year", "2019");
        hashMap3.put("type", "Other");
        arrayList.add(hashMap3);

        HashMap<String, String> hashMap4 = new HashMap<>();
        hashMap4.put("brand", "Hyundai");
        hashMap4.put("model", "Verna");
        hashMap4.put("regNo", "KA 05 MA 1234");
        hashMap4.put("year", "2013");
        hashMap4.put("type", "Other");
        arrayList.add(hashMap4);

        HashMap<String, String> hashMap6 = new HashMap<>();
        hashMap6.put("brand", "Hyundai");
        hashMap6.put("model", "i20 Active");
        hashMap6.put("regNo", "KA 05 MA 1234");
        hashMap6.put("year", "2014");
        hashMap6.put("type", "Other");
        arrayList.add(hashMap6);


        HashMap<String, String> hashMap5 = new HashMap<>();
        hashMap5.put("brand", "Hyundai");
        hashMap5.put("model", "Santro");
        hashMap5.put("regNo", "KA 05 MA 1234");
        hashMap5.put("year", "2011");
        hashMap5.put("type", "Other");
        arrayList.add(hashMap5);

        return arrayList;
    }

    public void vehicleDetailsDialog() {
        View view = getLayoutInflater().inflate(R.layout.vehicle_bottom_sheet_dialog, null);

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