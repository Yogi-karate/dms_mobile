package com.dealermanagmentsystem.ui.enquiry.vehicledetail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SpinnerCustomAdapter;
import com.dealermanagmentsystem.adapter.SpinnerVehicleAdapter;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity.detailResponse;
import static com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity.enquiryDetailRequest;

public class VehicleDetailActivity extends BaseActivity implements IVehicleView {

    @BindView(R.id.sp_product)
    Spinner spProduct;

    @BindView(R.id.sp_variant)
    Spinner spVariant;

    @BindView(R.id.sp_color)
    Spinner spColor;

    Activity activity;
    VehiclePresenter vehiclePresenter;
    String strProductId;
    String strProductName;
    String strVariantId;
    String strVariantName;
    String strColorId;
    String strColorName;
    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        ButterKnife.bind(this);
        activity = VehicleDetailActivity.this;
        showTile("Vehicle Details");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        vehiclePresenter = new VehiclePresenter(this);
        vehiclePresenter.getProduct(activity);

    }

    @Override
    public void onSuccessProducts(Response response) {
        final List<Record> productRecords = response.getRecords();

        SpinnerCustomAdapter customAdapter = new SpinnerCustomAdapter(activity, productRecords);
        spProduct.setAdapter(customAdapter);

        int pos = 0;
        for (int i = 0; i < productRecords.size(); i++) {
            if (productRecords.get(i).getName().equalsIgnoreCase(String.valueOf(detailResponse.getProductId().get(1)))) {
                pos = i;
            }
        }

        spProduct.setSelection(pos);

        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // productRecords.get(i).getName();
                productId = productRecords.get(i).getId();
                strProductId = String.valueOf(productId);
                strProductName = String.valueOf(productRecords.get(i).getName());
                vehiclePresenter.getVariants(activity, productId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onSuccessVariants(Response response) {
        final List<Record> variantRecords = response.getRecords();
        Record record = new Record();
        record.setDisplay_name("Select Variant");
        record.setName("Select Variant");
        record.setId(-1);
        variantRecords.add(0, record);
        SpinnerVehicleAdapter customAdapter = new SpinnerVehicleAdapter(activity, variantRecords);
        spVariant.setAdapter(customAdapter);

        int pos = -1;

        final Object productVariant = detailResponse.getProductVariant();
        if (productVariant instanceof List) {
            final String name = (String) ((List) productVariant).get(1);
            for (int i = 0; i < variantRecords.size(); i++) {
                if (String.valueOf(variantRecords.get(i).getDisplay_name()).equalsIgnoreCase(name)) {
                    pos = i;
                }
            }
            spVariant.setSelection(pos);
        }

        spVariant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final int variantId = variantRecords.get(i).getId();
                strVariantName = variantRecords.get(i).getName();
                strVariantId = String.valueOf(variantId);
                vehiclePresenter.getColors(activity, productId, variantId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessColors(Response response) {
        final List<Record> colorRecords = response.getRecords();
        Record record = new Record();
        record.setDisplay_name("Select Color");
        record.setName("Select Color");
        record.setId(-1);
        colorRecords.add(0, record);

        SpinnerVehicleAdapter customAdapter = new SpinnerVehicleAdapter(activity, colorRecords);
        spColor.setAdapter(customAdapter);

        int pos = -1;
        final Object productColor = detailResponse.getProductColor();
        if (productColor instanceof List) {
            final String name = (String) ((List) productColor).get(1);
            for (int i = 0; i < colorRecords.size(); i++) {
                if (colorRecords.get(i).getDisplay_name().equalsIgnoreCase(name)) {
                    pos = i;
                }
            }
            spColor.setSelection(pos);
        }


        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strColorId = String.valueOf(colorRecords.get(i).getId());
                strColorName = colorRecords.get(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @OnClick(R.id.save_vehicle) //ButterKnife uses.
    public void saveVehicle() {

        if (Integer.valueOf(strProductId) != (detailResponse.getProductId().get(0))) {
            List<Object> objectList = new ArrayList<>();
            objectList.add(Integer.valueOf(strProductId));
            objectList.add(strProductName);
            detailResponse.setProductId(objectList);
            enquiryDetailRequest.setProductId(Integer.valueOf(strProductId));
        }

    /*    final Object productVariant = detailResponse.getProductVariant();
        if (productVariant instanceof List) {
            final String name = (String) ((List) productVariant).get(1);
            if (!strVariantName.equalsIgnoreCase(name)) {
                List<Object> objectList = new ArrayList<>();
                objectList.add(Integer.valueOf(strVariantId));
                objectList.add(strVariantName);
                detailResponse.setProductVariant(objectList);
                enquiryDetailRequest.setProductVariant(strVariantId);
            }

        }*/

        if (!strVariantId.equalsIgnoreCase("-1")) {
            List<Object> objectListVariant = new ArrayList<>();
            objectListVariant.add(Integer.valueOf(strVariantId));
            objectListVariant.add(strVariantName);
            detailResponse.setProductVariant(objectListVariant);
            enquiryDetailRequest.setProductVariant(strVariantId);
        }


        //  if (!strColorId.equalsIgnoreCase(String.valueOf(detailResponse.getProductColor()))) {

        if (!strColorId.equalsIgnoreCase("-1")){
            List<Object> objectList = new ArrayList<>();
            objectList.add(Integer.valueOf(strColorId));
            objectList.add(strColorName);
            detailResponse.setProductColor(objectList);
            enquiryDetailRequest.setProductColor(strColorId);
        }

        //}

        finish();
    }

    @Override
    public void onError(String message) {

    }
}
