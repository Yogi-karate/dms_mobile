package com.bookservice.ui.activity.offer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.adapter.OfferDetailAdapter;
import com.bookservice.ui.activity.home.HomeActivity;
import com.bookservice.ui.activity.user.UserDetailActivity;
import com.bookservice.ui.activity.vehicle.VehicleDetailActivity;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bookservice.constants.Constants.EXTRA_DESCRIPTION;
import static com.bookservice.constants.Constants.EXTRA_NAME;

public class OfferDetailActivity extends BsBaseActivity {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;

    @BindView(R.id.select_date)
    TextView selectDate;

    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private GridLayoutManager gridLayoutManager;
    private OfferDetailAdapter offerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        showTile("Service Center Name");
        showWhiteStatusBar(Color.WHITE);
        showBackButton();

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        final Intent intent = getIntent();
        if (intent != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(EXTRA_NAME, intent.getStringExtra(EXTRA_NAME));
            hashMap.put(EXTRA_DESCRIPTION, intent.getStringExtra(EXTRA_DESCRIPTION));
            arrayList.add(hashMap);
        }

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        offerAdapter = new OfferDetailAdapter(arrayList);
        recyclerView.setAdapter(offerAdapter);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar, selectDate);

            }
        };
    }

    @OnClick(R.id.select_date) //ButterKnife uses.
    public void selectBookingDate() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(OfferDetailActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();
    }


    @OnClick(R.id.book_offer) //ButterKnife uses.
    public void bookOffer() {
        bookingConfirmedDialog();
    }


    @OnClick(R.id.share) //ButterKnife uses.
    public void openShare() {
        Utils.share(OfferDetailActivity.this);
    }

    @OnClick(R.id.edit_customer) //ButterKnife uses.
    public void openEditCustomer() {
        Intent intent = new Intent(OfferDetailActivity.this, UserDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.edit_vehicle) //ButterKnife uses.
    public void openEditVehicle() {
        Intent intent = new Intent(OfferDetailActivity.this, VehicleDetailActivity.class);
        startActivity(intent);
    }

    private void updateLabel(Calendar myCalendar, TextView etDate) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(myCalendar.getTime()));
    }


    public void bookingConfirmedDialog() {
        final View alertContentView = getLayoutInflater().inflate(R.layout.dialog_confirmed, null);
        final Dialog dialog = new Dialog(OfferDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(alertContentView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        final LinearLayout continueHomePage = (LinearLayout) alertContentView.findViewById(R.id.continue_home_page);
        continueHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferDetailActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        // retrieve display dimensions
       /* Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        alertContentView.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
        dialog.setContentView(alertContentView);

        dialog.show();*/
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
