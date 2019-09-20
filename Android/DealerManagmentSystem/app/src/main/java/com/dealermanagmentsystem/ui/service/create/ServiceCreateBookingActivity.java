package com.dealermanagmentsystem.ui.service.create;

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

public class ServiceCreateBookingActivity extends BaseActivity implements IServiceCreateBookingView {

    Activity activity;
    ServiceCreateBookingPresenter presenter;
    @BindView(R.id.et_pick_up_date)
    TextView txtPickUpDate;
    private Calendar myCalendar;
    @BindView(R.id.sp_booking_type)
    Spinner spBookingType;
    @BindView(R.id.sp_service_type)
    Spinner spServiceType;

    @BindView(R.id.et_pick_up_address)
    EditText etPickUpAddress;
    @BindView(R.id.sp_location)
    Spinner spLocation;

    @BindView(R.id.et_remarks)
    EditText etRemarks;
    String strBookingTypeId;
    String strServiceTypeId;
    int locationId;
    String strLeadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_create_booking);

        activity = ServiceCreateBookingActivity.this;
        ButterKnife.bind(this);
        setStatusBarColor(getResources().getColor(R.color.bg));
        showTile("Create Booking");
        showBackButton();

        final Intent intent = getIntent();
        if (intent != null) {
            strLeadId = intent.getStringExtra(EXTRA_LEAD_ID);
        }

        myCalendar = Calendar.getInstance();

        presenter = new ServiceCreateBookingPresenter(this);
        presenter.getBookingType();
        presenter.getServiceType();
        presenter.getLocation(activity);
    }

    @OnClick(R.id.et_pick_up_date) //ButterKnife uses.
    public void selectPickUpDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd hh:mm:ss";//In which you need put here "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                // txtPickUpDate.setText(sdf.format(myCalendar.getTime()));
                timePicker(myCalendar);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private void timePicker(final Calendar myCalendar) {
        // Get Current Time
        //final Calendar c = Calendar.getInstance();
       /* int  mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
*/
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalendar.set(Calendar.MINUTE, minute);
                    /*    mHour = hourOfDay;
                        mMinute = minute;*/
                        String myFormat = "yyyy-MM-dd HH:mm:ss";//In which you need put here "MM/dd/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        txtPickUpDate.setText(sdf.format(myCalendar.getTime()));
                    }
                }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
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
                  //  txtPickUpDate.setVisibility(View.VISIBLE);

                } else if (i == 1) {
                    etPickUpAddress.setVisibility(View.GONE);
                   // txtPickUpDate.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessServiceType(final ArrayList<HashMap<String, String>> list) {
        SpinnerMapAdapter customAdapter = new SpinnerMapAdapter(activity, list);
        spServiceType.setAdapter(customAdapter);

        spServiceType.setSelection(0);

        spServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strServiceTypeId = list.get(i).get(KEY_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessServiceLocation(Response response) {
        final List<Record> locationRecords = response.getRecords();

        SpinnerCustomAdapter customAdapter = new SpinnerCustomAdapter(activity, locationRecords);
        spLocation.setAdapter(customAdapter);

        spLocation.setSelection(0);

        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locationId = locationRecords.get(i).getId();

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

    @OnClick(R.id.submit_booking) //ButterKnife uses.
    public void createBooking() {
        String strDop = txtPickUpDate.getText().toString();
        String strAddress = etPickUpAddress.getText().toString();
        String strRemarks = etRemarks.getText().toString();
        presenter.createServiceBooking(activity, locationId, strBookingTypeId, strDop,
                strAddress, strRemarks, strLeadId, strServiceTypeId);
    }

    @Override
    public void onSuccessServiceCreateBooking(CommonResponse response) {
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
        title.setText("Service booked successfully");
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
