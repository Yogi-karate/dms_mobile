package com.dealermanagmentsystem.ui.enquiry.insurancedetail;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.adapter.SpinnerMapAdapter;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.financedetail.IFinanceView;
import com.dealermanagmentsystem.ui.enquiry.vehicledetail.VehicleDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.KEY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_NAME;
import static com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity.detailResponse;
import static com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity.enquiryDetailRequest;

public class InsuranceDetailActivity extends BaseActivity implements IInsuranceView {

    @BindView(R.id.sp_insurance_type)
    Spinner spInsuranceType;
    @BindView(R.id.sp_policy_punch)
    Spinner spPolicyPunch;

    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_policy_no)
    EditText etPolicyNo;
    @BindView(R.id.et_idv)
    EditText etIdv;
    @BindView(R.id.et_insurance_valid_from)
    TextView txtInsuranceValidFrom;
    @BindView(R.id.et_insurance_valid_to)
    TextView txtInsuranceValidTo;
    @BindView(R.id.et_premium_amount)
    EditText etPremiumAmount;

    Activity activity;
    InsurancePresenter insurancePresenter;

    private Calendar myCalendarFrom;
    private Calendar myCalendarTo;

    String strInsuranceTypeId;
   // String strInsuranceTypeName;
  //  String strInsurancePunchName;
    String strInsurancePunchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        ButterKnife.bind(this);
        activity = InsuranceDetailActivity.this;
        showTile("Insurance Details");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        myCalendarFrom = Calendar.getInstance();
        myCalendarTo = Calendar.getInstance();

        insurancePresenter = new InsurancePresenter(this);
        insurancePresenter.getInsurancePunch(activity);
        insurancePresenter.getInsuranceType(activity);

        etCompany.setText(detailResponse.getInsuranceCompany());
        etPolicyNo.setText(detailResponse.getPolicyNo());
        etIdv.setText(String.valueOf(detailResponse.getIdv()));
        txtInsuranceValidFrom.setText(detailResponse.getInsuranceValidFrom());
        txtInsuranceValidTo.setText(detailResponse.getInsuranceValidTo());
        etPremiumAmount.setText(String.valueOf(detailResponse.getPremiumAmount()));
    }


    @OnClick(R.id.save_insurance) //ButterKnife uses.
    public void saveInsurance() {

        final String strInsuranceCompany = etCompany.getText().toString();
        if (!strInsuranceCompany.equalsIgnoreCase(detailResponse.getInsuranceCompany())) {
            detailResponse.setInsuranceCompany(strInsuranceCompany);
            enquiryDetailRequest.setInsuranceCompany(strInsuranceCompany);
        }

        final String strPolicyNo = etPolicyNo.getText().toString();
        if (!strPolicyNo.equalsIgnoreCase(detailResponse.getPolicyNo())) {
            detailResponse.setPolicyNo(strPolicyNo);
            enquiryDetailRequest.setPolicyNo(strPolicyNo);
        }

        final String strIDV = etIdv.getText().toString();
        if (!strIDV.equalsIgnoreCase(String.valueOf(detailResponse.getIdv()))) {
            detailResponse.setIdv(Double.valueOf(strIDV));
            enquiryDetailRequest.setIdv(Double.valueOf(strIDV));
        }

        final String strPremiumAmount = etPremiumAmount.getText().toString();
        if (!strPremiumAmount.equalsIgnoreCase(String.valueOf(detailResponse.getPremiumAmount()))) {
            detailResponse.setPremiumAmount(Double.valueOf(strPremiumAmount));
            enquiryDetailRequest.setPremiumAmount(Double.valueOf(strPremiumAmount));
        }

        final String strInsuranceValidFrom = txtInsuranceValidFrom.getText().toString();
        if (!strInsuranceValidFrom.equalsIgnoreCase(String.valueOf(detailResponse.getInsuranceValidFrom()))) {
            detailResponse.setInsuranceValidFrom(strInsuranceValidFrom);
            enquiryDetailRequest.setInsuranceValidFrom(strInsuranceValidFrom);
        }

        final String strInsuranceValidTo = txtInsuranceValidTo.getText().toString();
        if (!strInsuranceValidTo.equalsIgnoreCase(String.valueOf(detailResponse.getInsuranceValidTo()))) {
            detailResponse.setInsuranceValidTo(strInsuranceValidTo);
            enquiryDetailRequest.setInsuranceValidTo(strInsuranceValidTo);
        }


        if (!strInsuranceTypeId.equalsIgnoreCase(detailResponse.getInsuranceType())) {
            enquiryDetailRequest.setInsuranceType(strInsuranceTypeId);
            detailResponse.setInsuranceType(strInsuranceTypeId);
        }

        if (!strInsurancePunchId.equalsIgnoreCase(detailResponse.getPolicyPunchVia())) {
            enquiryDetailRequest.setPolicyPunchVia(strInsurancePunchId);
            detailResponse.setPolicyPunchVia(strInsurancePunchId);
        }

        finish();

    }


    @Override
    public void onSuccessInsurancePunch(final ArrayList<HashMap<String, String>> list) {
        SpinnerMapAdapter customAdapter = new SpinnerMapAdapter(activity, list);
        spPolicyPunch.setAdapter(customAdapter);

        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(KEY_ID).equalsIgnoreCase(detailResponse.getPolicyPunchVia())) {
                pos = i;
            }
        }

        spPolicyPunch.setSelection(pos);

        spPolicyPunch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // strInsurancePunchName = list.get(i).get(KEY_NAME);
                strInsurancePunchId = list.get(i).get(KEY_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessInsuranceType(final ArrayList<HashMap<String, String>> list) {
        SpinnerMapAdapter customAdapter = new SpinnerMapAdapter(activity, list);
        spInsuranceType.setAdapter(customAdapter);

        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(KEY_ID).equalsIgnoreCase(detailResponse.getInsuranceType())) {
                pos = i;
            }
        }
        spInsuranceType.setSelection(pos);

        spInsuranceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              //  strInsuranceTypeName = list.get(i).get(KEY_NAME);
                strInsuranceTypeId = list.get(i).get(KEY_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onError(String message) {

    }

    @OnClick(R.id.et_insurance_valid_from) //ButterKnife uses.
    public void selectFromDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarFrom.set(Calendar.YEAR, year);
                myCalendarFrom.set(Calendar.MONTH, monthOfYear);
                myCalendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";//In which you need put here "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txtInsuranceValidFrom.setText(sdf.format(myCalendarFrom.getTime()));

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendarFrom
                .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                myCalendarFrom.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.et_insurance_valid_to) //ButterKnife uses.
    public void selectToDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";//In which you need put here "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txtInsuranceValidTo.setText(sdf.format(myCalendarTo.getTime()));

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendarTo
                .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                myCalendarTo.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

}
