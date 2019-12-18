package com.dealermanagmentsystem.ui.saleorder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SpinnerCustomAdapter;
import com.dealermanagmentsystem.adapter.SpinnerMapAdapter;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.financedetail.FinancePresenter;
import com.dealermanagmentsystem.ui.enquiry.financedetail.IFinanceView;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_BOOKING_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_DELIVERY_DATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FINANCE_TYPE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FINANCIER;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_PAYMENT_DATE;
import static com.dealermanagmentsystem.constants.Constants.KEY_CREATE_ACTIVITY;
import static com.dealermanagmentsystem.constants.Constants.KEY_ID;

public class SaleOrderEditActivity extends BaseActivity implements IFinanceView, ISaleOrderEditView {
    Activity activity;
    String id, paymentDate = "", deliveryDate = "", financeType = "", financier = "";

    @BindView(R.id.sp_finance_type)
    Spinner spFinanceType;
    @BindView(R.id.sp_financier)
    Spinner spFinancier;
    @BindView(R.id.et_payment_date)
    TextView etPaymentDate;
    @BindView(R.id.et_delivery_date)
    TextView etDeliveryDate;

    FinancePresenter financePresenter;
    SaleOrderEditPresenter saleOrderEditPresenter;
    private Calendar paymentDateCalendar;
    private Calendar deliveryDateCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order_edit);

        activity = SaleOrderEditActivity.this;
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(EXTRA_BOOKING_ID);
            paymentDate = intent.getStringExtra(EXTRA_PAYMENT_DATE);
            deliveryDate = intent.getStringExtra(EXTRA_DELIVERY_DATE);
            financeType = intent.getStringExtra(EXTRA_FINANCE_TYPE);
            financier = intent.getStringExtra(EXTRA_FINANCIER);

            etPaymentDate.setText(paymentDate);
            etDeliveryDate.setText(deliveryDate);

        }

        showTile("Update details");

        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        paymentDateCalendar = Calendar.getInstance();
        deliveryDateCalendar = Calendar.getInstance();

        financePresenter = new FinancePresenter(this);
        saleOrderEditPresenter = new SaleOrderEditPresenter(this);
        financePresenter.getFinancier(activity);
        financePresenter.getFinanceType(activity);

    }


    @OnClick(R.id.update) //ButterKnife uses.
    public void updateFinanceData() {
        final String sPaymentDate = etPaymentDate.getText().toString();
        final String sDeliveryDate = etDeliveryDate.getText().toString();

        saleOrderEditPresenter.postSaleOrderEdit(activity, sPaymentDate, sDeliveryDate, financeType, financier, id);
    }

    @OnClick(R.id.et_payment_date) //ButterKnife uses.
    public void selectPaymentDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                paymentDateCalendar.set(Calendar.YEAR, year);
                paymentDateCalendar.set(Calendar.MONTH, monthOfYear);
                paymentDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(paymentDateCalendar, etPaymentDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, paymentDateCalendar
                .get(Calendar.YEAR), paymentDateCalendar.get(Calendar.MONTH),
                paymentDateCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.et_delivery_date) //ButterKnife uses.
    public void selectDeliveryDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                deliveryDateCalendar.set(Calendar.YEAR, year);
                deliveryDateCalendar.set(Calendar.MONTH, monthOfYear);
                deliveryDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(deliveryDateCalendar, etDeliveryDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, deliveryDateCalendar
                .get(Calendar.YEAR), deliveryDateCalendar.get(Calendar.MONTH),
                deliveryDateCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private void timePicker(final Calendar myCalendar, final TextView txtView) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalendar.set(Calendar.MINUTE, minute);

                        String myFormat = "yyyy-MM-dd HH:mm:ss";//In which you need put here "MM/dd/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        txtView.setText(sdf.format(myCalendar.getTime()));
                    }
                }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onSuccessFinanceType(final ArrayList<HashMap<String, String>> list) {
        SpinnerMapAdapter customAdapter = new SpinnerMapAdapter(activity, list);
        spFinanceType.setAdapter(customAdapter);

        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(KEY_ID).equalsIgnoreCase(financeType)) {
                pos = i;
            }
        }

        spFinanceType.setSelection(pos);

        spFinanceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                financeType = list.get(i).get(KEY_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessFinancier(Response response) {
        final List<Record> recordsFinancier = response.getRecords();
        SpinnerCustomAdapter customAdapter = new SpinnerCustomAdapter(activity, recordsFinancier);
        spFinancier.setAdapter(customAdapter);

        int pos = 0;
        for (int i = 0; i < recordsFinancier.size(); i++) {
            if (String.valueOf(recordsFinancier.get(i).getId()).equalsIgnoreCase(financier)) {
                pos = i;
            }
        }

        spFinancier.setSelection(pos);

        spFinancier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                financier = String.valueOf(recordsFinancier.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @Override
    public void onSuccessSaleOrderEdit(CommonResponse response) {
        if (TextUtils.isEmpty(response.getError())) {
            DMSToast.showLong(activity, "Update Successfully");
            finish();
        } else {
            DMSToast.showLong(activity, "Failed to update");
        }
    }

    @Override
    public void onErrorMessage(String message) {
        DMSToast.showLong(activity, message);
    }
}
