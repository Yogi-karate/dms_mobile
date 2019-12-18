package com.dealermanagmentsystem.ui.quotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SpinnerCustomAdapter;
import com.dealermanagmentsystem.adapter.SpinnerVehicleAdapter;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.stock.StockResponse;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.vehicledetail.IVehicleView;
import com.dealermanagmentsystem.ui.enquiry.vehicledetail.VehiclePresenter;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_EMAIL;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_MOBILE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_MODEL_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_PARTNER_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;

public class QuotationCreateActivity extends BaseActivity implements IVehicleView, IQuotationCreateView {

    @BindView(R.id.sp_product)
    Spinner spProduct;

    @BindView(R.id.sp_variant)
    Spinner spVariant;

    @BindView(R.id.sp_color)
    Spinner spColor;

    @BindView(R.id.sp_price_list)
    Spinner spPriceList;

    @BindView(R.id.submit_quotation)
    Button btnCreateQuotation;

    Activity activity;
    VehiclePresenter vehiclePresenter;
    QuotationCreatePresenter quotationCreatePresenter;
    String strProductId;
    String strProductName;
    String strVariantId;
    String strVariantName;
    String strColorId;
    String strColorName;

    String strPriceListId;
    String strPriceListName;

    int productId;
    int variantId;
    int colorId;
    int priceListId;

    String strPartnerName, strMobile, strEmail, strModelId;
    int id, strTeamId, strUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_create);

        ButterKnife.bind(this);
        activity = QuotationCreateActivity.this;
        showTile("Create Quotation");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        final Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(EXTRA_ENQUIRY_ID, 0);
            strTeamId = intent.getIntExtra(EXTRA_TEAM_ID, 0);
            strUserId = intent.getIntExtra(EXTRA_USER_ID, 0);
            strMobile = intent.getStringExtra(EXTRA_MOBILE);
            strEmail = intent.getStringExtra(EXTRA_EMAIL);
            strPartnerName = intent.getStringExtra(EXTRA_PARTNER_NAME);
            strModelId = intent.getStringExtra(EXTRA_MODEL_ID);
        }

        vehiclePresenter = new VehiclePresenter(this);
        quotationCreatePresenter = new QuotationCreatePresenter(this);
        vehiclePresenter.getProduct(activity);
        quotationCreatePresenter.getPriceList(activity);

    }

    @Override
    public void onSuccessProducts(Response response) {
        final List<Record> productRecords = response.getRecords();

        SpinnerCustomAdapter customAdapter = new SpinnerCustomAdapter(activity, productRecords);
        spProduct.setAdapter(customAdapter);


        int pos = 0;
        for (int i = 0; i < productRecords.size(); i++) {
            if (String.valueOf(productRecords.get(i).getId()).equalsIgnoreCase(strModelId)) {
                pos = i;
            }
        }
        spProduct.setSelection(pos);

        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

        int pos = 0;

     /*   final Object productVariant = detailResponse.getProductVariant();
        if (productVariant instanceof List) {
            final String name = (String) ((List) productVariant).get(1);
            for (int i = 0; i < variantRecords.size(); i++) {
                if (String.valueOf(variantRecords.get(i).getDisplay_name()).equalsIgnoreCase(name)) {
                    pos = i;
                }
            }

        }*/
        spVariant.setSelection(pos);
        spVariant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                variantId = variantRecords.get(i).getId();
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
        int pos = 0;
      /*
        final Object productColor = detailResponse.getProductColor();
        if (productColor instanceof List) {
            final String name = (String) ((List) productColor).get(1);
            for (int i = 0; i < colorRecords.size(); i++) {
                if (colorRecords.get(i).getDisplay_name().equalsIgnoreCase(name)) {
                    pos = i;
                }
            }

        }*/
        spColor.setSelection(pos);

        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                colorId = colorRecords.get(i).getId();
                strColorId = String.valueOf(colorRecords.get(i).getId());
                strColorName = colorRecords.get(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onPriceListSuccess(Response response) {
        final List<Record> priceListRecords = response.getRecords();
        Record record = new Record();
        record.setDisplay_name("Select Price List");
        record.setName("Select Price List");
        record.setId(-1);
        priceListRecords.add(0, record);

        boolean isWithoutDisplayName = true;
        SpinnerVehicleAdapter customAdapter = new SpinnerVehicleAdapter(activity, priceListRecords, isWithoutDisplayName);
        spPriceList.setAdapter(customAdapter);

        int pos = 0;

        spPriceList.setSelection(pos);
        spPriceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceListId = priceListRecords.get(i).getId();
                strPriceListName = priceListRecords.get(i).getName();
                strPriceListId = String.valueOf(variantId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.submit_quotation) //ButterKnife uses.
    public void createQuotation() {
        quotationCreatePresenter.createQuotation(activity, productId, variantId, colorId, priceListId, id
                , strMobile, strPartnerName, strEmail, strTeamId, strUserId);
    }

    @Override
    public void onQuotationSuccess() {
        DMSToast.showLong(activity, "Quotation created successfully");
        finish();
    }


    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }
}