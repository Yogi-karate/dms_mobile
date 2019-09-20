package com.dealermanagmentsystem.ui.insurance.create;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.dealermanagmentsystem.ui.service.create.IServiceCreateBookingView;
import com.dealermanagmentsystem.ui.service.create.ServiceCreateBookingPresenter;
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

import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_ID;

public class InsuranceCreateBookingActivity extends BaseActivity implements IInsuranceCreateBookingView {

    Activity activity;

   /* ServiceCreateBookingPresenter presenter;
    @BindView(R.id.et_pick_up_date)
    TextView txtPickUpDate;
    private Calendar myCalendar;
    @BindView(R.id.sp_booking_type)
    Spinner spBookingType;

    @BindView(R.id.et_pick_up_address)
    EditText etPickUpAddress;
    @BindView(R.id.sp_location)
    Spinner spLocation;

    @BindView(R.id.et_remarks)
    EditText etRemarks;
    String strBookingTypeId;
    int locationId;
    String strLeadId;*/

    // previous insurance views
    @BindView(R.id.et_previous_idv)
    EditText etPreviousIDV;
    @BindView(R.id.et_previous_ncb)
    EditText etPreviousNCB;
    @BindView(R.id.et_discount)
    EditText etDiscount;
    @BindView(R.id.sp_previous_insurance_company)
    Spinner spPreviousInsuranceCompany;
    @BindView(R.id.sp_previous_nil_dip)
    Spinner spPreviousNilDip;
    @BindView(R.id.et_previous_due_date)
    TextView etPreviousDueDate;
    @BindView(R.id.et_previous_final_premium)
    EditText etPreviousFinalPremium;
    @BindView(R.id.et_previous_policy_no)
    EditText etPreviousPolicyNo;

    //current Insurance company
    @BindView(R.id.et_current_idv)
    EditText etCurrentIDV;
    @BindView(R.id.et_current_ncb)
    EditText etCurrentNCB;
    @BindView(R.id.sp_current_insurance_company)
    Spinner spCurrentInsuranceCompany;
    @BindView(R.id.et_alternate_number)
    EditText etAlternateNumber;
    @BindView(R.id.sp_current_nil_dip)
    Spinner spCurrentNilDip;
    @BindView(R.id.et_current_due_date)
    TextView etCurrentDueDate;
    @BindView(R.id.et_current_final_premium)
    EditText etCurrentFinalPremium;
    @BindView(R.id.sp_booking_type)
    Spinner spBookingType;
    @BindView(R.id.et_pick_up_date)
    TextView etPickUpDate;
    @BindView(R.id.et_pick_up_address)
    EditText etPickUpAddress;


    String strLeadId;
    private Calendar insurancePreviousDueDateCalendar;
    private Calendar insuranceCurrentDueDateCalendar;
    private Calendar dateOfPickCalendar;

    InsuranceCreateBookingPresenter presenter;

    String strBookingTypeId;
    String strPreviousNilDip;
    String strCurrentNilDip;
    int previousCompanyId;
    int currentCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_create_booking);

        activity = InsuranceCreateBookingActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Create Booking");
        showBackButton();

        final Intent intent = getIntent();
        if (intent != null) {
            strLeadId = intent.getStringExtra(EXTRA_LEAD_ID);
        }

        insurancePreviousDueDateCalendar = Calendar.getInstance();
        insuranceCurrentDueDateCalendar = Calendar.getInstance();
        dateOfPickCalendar = Calendar.getInstance();


        presenter = new InsuranceCreateBookingPresenter(this);
        presenter.getBookingType();
        presenter.getNilDip();
        presenter.getBanks(activity);
    }


    @Override
    public void onSuccessBookingType(final ArrayList<HashMap<String, String>> list) {
        SpinnerMapAdapter customAdapter = new SpinnerMapAdapter(activity, list);
        spBookingType.setAdapter(customAdapter);

        spBookingType.setSelection(0);

        spBookingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strBookingTypeId = list.get(i).get(KEY_ID);
                if (i == 0) {
                    etPickUpAddress.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    etPickUpAddress.setVisibility(View.GONE);
                }else if (i == 2) {
                    etPickUpAddress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessNilDipType(final ArrayList<HashMap<String, String>> list) {
        SpinnerMapAdapter customAdapter = new SpinnerMapAdapter(activity, list);
        spPreviousNilDip.setAdapter(customAdapter);

        spPreviousNilDip.setSelection(0);

        spPreviousNilDip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strPreviousNilDip = list.get(i).get(KEY_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SpinnerMapAdapter customAdapter1 = new SpinnerMapAdapter(activity, list);
        spCurrentNilDip.setAdapter(customAdapter1);

        spCurrentNilDip.setSelection(0);

        spCurrentNilDip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strCurrentNilDip = list.get(i).get(KEY_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessBank(Response response) {
        final List<Record> locationRecords = response.getRecords();

        SpinnerCustomAdapter customAdapter = new SpinnerCustomAdapter(activity, locationRecords);
        spPreviousInsuranceCompany.setAdapter(customAdapter);

        spPreviousInsuranceCompany.setSelection(0);

        spPreviousInsuranceCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                previousCompanyId = locationRecords.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SpinnerCustomAdapter customAdapter1 = new SpinnerCustomAdapter(activity, locationRecords);
        spCurrentInsuranceCompany.setAdapter(customAdapter1);

        spCurrentInsuranceCompany.setSelection(0);

        spCurrentInsuranceCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCompanyId = locationRecords.get(i).getId();

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


    @OnClick(R.id.et_previous_due_date) //ButterKnife uses.
    public void selectPreviousDueDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                insurancePreviousDueDateCalendar.set(Calendar.YEAR, year);
                insurancePreviousDueDateCalendar.set(Calendar.MONTH, monthOfYear);
                insurancePreviousDueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(insurancePreviousDueDateCalendar, etPreviousDueDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, insurancePreviousDueDateCalendar
                .get(Calendar.YEAR), insurancePreviousDueDateCalendar.get(Calendar.MONTH),
                insurancePreviousDueDateCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.et_current_due_date) //ButterKnife uses.
    public void selectCurrentDueDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                insurancePreviousDueDateCalendar.set(Calendar.YEAR, year);
                insurancePreviousDueDateCalendar.set(Calendar.MONTH, monthOfYear);
                insurancePreviousDueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(insurancePreviousDueDateCalendar, etCurrentDueDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, insurancePreviousDueDateCalendar
                .get(Calendar.YEAR), insurancePreviousDueDateCalendar.get(Calendar.MONTH),
                insurancePreviousDueDateCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.et_pick_up_date) //ButterKnife uses.
    public void selectPickUpDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                insurancePreviousDueDateCalendar.set(Calendar.YEAR, year);
                insurancePreviousDueDateCalendar.set(Calendar.MONTH, monthOfYear);
                insurancePreviousDueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(insurancePreviousDueDateCalendar, etPickUpDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, insurancePreviousDueDateCalendar
                .get(Calendar.YEAR), insurancePreviousDueDateCalendar.get(Calendar.MONTH),
                insurancePreviousDueDateCalendar.get(Calendar.DAY_OF_MONTH));

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

    @OnClick(R.id.submit_booking) //ButterKnife uses.
    public void createBooking() {
        String strPreviousIDV = etPreviousIDV.getText().toString();
        String strPreviousNCB = etPickUpAddress.getText().toString();
        String strDiscount = etDiscount.getText().toString();
        String strPreviousDueDate = etPreviousDueDate.getText().toString();
        String strPreviousFinalPremium = etPreviousFinalPremium.getText().toString();
        String strPreviousPolicyNo = etPreviousPolicyNo.getText().toString();

        String strCurrentIDV = etCurrentIDV.getText().toString();
        String strCurrentNCB = etCurrentNCB.getText().toString();
        String strAlternateNumber = etAlternateNumber.getText().toString();
        String strCurrentDueDate = etCurrentDueDate.getText().toString();
        String strCurrentFinalPremium = etCurrentFinalPremium.getText().toString();
        String strPickUpDate = etPickUpDate.getText().toString();
        String strPickUpAddress = etPickUpAddress.getText().toString();

        presenter.createInsuranceBooking(activity, strLeadId, previousCompanyId, strPreviousNilDip, strPreviousIDV, strPreviousNCB,
                strDiscount, strPreviousDueDate, strPreviousFinalPremium, strPreviousPolicyNo, currentCompanyId,
                strCurrentNilDip, strBookingTypeId, strCurrentIDV, strCurrentNCB, strAlternateNumber, strCurrentDueDate,
                strCurrentFinalPremium, strPickUpDate, strPickUpAddress);
    }

    @Override
    public void onSuccessInsuranceCreateBooking(CommonResponse response) {
        if (response.getId() != null && response.getId() > 0) {
            successDialog(String.valueOf(response.getId()));
        } else {
            onError("Something went wrong, please try after sometime");
        }
    }

    public void successDialog(String id) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_enquiry_sucess, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        final Button ok = view.findViewById(R.id.ok);
        final TextView name = view.findViewById(R.id.name);
        final TextView title = view.findViewById(R.id.tv_title);
        title.setText("Insurance booked successfully");
        name.setText("Booking id is " + id);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Rect displayRectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
        dialog.setContentView(view);

        dialog.show();
    }

}
