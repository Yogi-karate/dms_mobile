package com.dealermanagmentsystem.ui.enquiry.financedetail;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SpinnerCustomAdapter;
import com.dealermanagmentsystem.adapter.SpinnerMapAdapter;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.KEY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_NAME;
import static com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity.detailResponse;
import static com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity.enquiryDetailRequest;

public class FinanceDetailActivity extends BaseActivity implements IFinanceView {

    Activity activity;
    FinancePresenter financePresenter;
    private Calendar myCalendar;

    @BindView(R.id.sp_finance_type)
    Spinner spFinanceType;
    @BindView(R.id.sp_financier)
    Spinner spFinancier;
    @BindView(R.id.et_finance_date)
    TextView etFinanceDate;
    @BindView(R.id.et_amount)
    TextView etAmount;

    String strFinanceTypeId;
    String strFinanceTypeName;
    String strFinancierName;
    int strFinancierNameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        ButterKnife.bind(this);
        activity = FinanceDetailActivity.this;
        showTile("Insurance Details");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        myCalendar = Calendar.getInstance();

        financePresenter = new FinancePresenter(this);
        financePresenter.getFinancier(activity);
        financePresenter.getFinanceType(activity);

        etFinanceDate.setText(detailResponse.getFinanceAgreementDate());
        etAmount.setText(String.valueOf(detailResponse.getFinanceAmount()));
    }

    @Override
    public void onSuccessFinancier(Response response) {
        final List<Record> recordsFinancier = response.getRecords();
        SpinnerCustomAdapter customAdapter = new SpinnerCustomAdapter(activity, recordsFinancier);
        spFinancier.setAdapter(customAdapter);

        int pos = 0;

        final Object financierName = detailResponse.getFinancierName();
        if (financierName instanceof List) {
            final String name = (String) ((List) financierName).get(1);
            for (int i = 0; i < recordsFinancier.size(); i++) {
                if (recordsFinancier.get(i).getName().equalsIgnoreCase(name)) {
                    pos = i;
                }
            }
        }

        spFinancier.setSelection(pos);

        spFinancier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strFinancierName = recordsFinancier.get(i).getName();
                strFinancierNameId = recordsFinancier.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessFinanceType(final ArrayList<HashMap<String, String>> list) {
        SpinnerMapAdapter customAdapter = new SpinnerMapAdapter(activity, list);
        spFinanceType.setAdapter(customAdapter);

        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(KEY_ID).equalsIgnoreCase(detailResponse.getFinanceType())) {
                pos = i;
            }
        }

        spFinanceType.setSelection(pos);

        spFinanceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strFinanceTypeId = list.get(i).get(KEY_ID);
                strFinanceTypeName = list.get(i).get(KEY_NAME);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.save_finance) //ButterKnife uses.
    public void saveFinance() {
        final String strFinanceDate = etFinanceDate.getText().toString();

        if (!strFinanceDate.equalsIgnoreCase(detailResponse.getFinanceAgreementDate())) {
            detailResponse.setFinanceAgreementDate(strFinanceDate);
            enquiryDetailRequest.setFinanceAgreementDate(strFinanceDate);
        }

        final String strFinanceAmount = etAmount.getText().toString();
        if (!strFinanceAmount.equalsIgnoreCase(String.valueOf(detailResponse.getFinanceAmount()))) {
            detailResponse.setFinanceAmount(Double.valueOf(strFinanceAmount));
            enquiryDetailRequest.setFinanceAmount(Double.valueOf(strFinanceAmount));
        }

     /*   final Object financierName = detailResponse.getFinancierName();
        if (financierName instanceof List) {
            if (!strFinancierName.equalsIgnoreCase(String.valueOf(((List) financierName).get(1)))) {
                List<Object> objectList = new ArrayList<>();
                objectList.add(Integer.valueOf(strFinancierNameId));
                objectList.add(strFinancierName);

                enquiryDetailRequest.setFinancierName(strFinancierNameId);
                detailResponse.setFinancierName(strFinancierName);
            }
        }*/

        List<Object> objectList = new ArrayList<>();
        objectList.add(Integer.valueOf(strFinancierNameId));
        objectList.add(strFinancierName);
        detailResponse.setFinancierName(objectList);
        enquiryDetailRequest.setFinancierName(strFinancierNameId);

        if (!strFinanceTypeId.equalsIgnoreCase(detailResponse.getFinanceType())) {
            enquiryDetailRequest.setFinanceType(strFinanceTypeId);
            detailResponse.setFinanceType(strFinanceTypeId);
        }

        finish();

    }


    @Override
    public void onError(String message) {

    }

    @OnClick(R.id.et_finance_date) //ButterKnife uses.
    public void selectFinanceDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";//In which you need put here "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                etFinanceDate.setText(sdf.format(myCalendar.getTime()));

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }


}
