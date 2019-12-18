package com.dealermanagmentsystem.ui.saleorder.detail.booking;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.sobooking.SOBookingResponse;
import com.dealermanagmentsystem.ui.base.BaseFragment;
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

import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_ORDER_STATE;
import static com.dealermanagmentsystem.constants.Constants.KEY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_NAME;


public class BookingFragment extends BaseFragment implements IBookingView, IFinanceView {


    @BindView(R.id.finance_type)
    TextView financeType;
    @BindView(R.id.financier)
    TextView financier;
    @BindView(R.id.finance_amount)
    TextView financeAmount;
    @BindView(R.id.finance_payment_date)
    TextView financePaymentDate;
    @BindView(R.id.margin_money_amount)
    TextView marginMoneyAmount;
    @BindView(R.id.margin_money_date)
    TextView marginMoneyDate;
    @BindView(R.id.delivery_date)
    TextView deliveryDate;
    @BindView(R.id.allotted)
    TextView allotted;
    @BindView(R.id.bal_amount)
    TextView balAmount;
    @BindView(R.id.priority)
    TextView priority;
    @BindView(R.id.booking_amount)
    TextView bookingAmount;
    @BindView(R.id.booking_date)
    TextView bookingDate;

    @BindView(R.id.update)
    Button btnUpdate;

    Activity activity;
    BookingPresenter presenter;
    FinancePresenter financePresenter;

    String strSaleOrderId;
    String strSaleOrderState;

    private Calendar financePaymentDateCalendar;
    private Calendar deliveryDateCalendar;
    private Calendar marginMoneyDateCalendar;


    String strFinanceTypeId, strFinancierId;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        strSaleOrderId = getArguments().getString(EXTRA_SALE_ORDER_ID);
        strSaleOrderState = getArguments().getString(EXTRA_SALE_ORDER_STATE);

        presenter = new BookingPresenter(this);
        financePresenter = new FinancePresenter(this);

        financePaymentDateCalendar = Calendar.getInstance();
        deliveryDateCalendar = Calendar.getInstance();
        marginMoneyDateCalendar = Calendar.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getBookingDetails(activity, strSaleOrderId);
        financePresenter.getFinancier(activity);
        financePresenter.getFinanceType(activity);

        if (strSaleOrderState.equalsIgnoreCase("draft")) {
            btnUpdate.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onSuccessBookingDetails(SOBookingResponse bookingResponse) {

        if (bookingResponse != null) {
            final Object financierName = bookingResponse.getFinancierName();
            int iFinancierId = 0;
            Double d;
            if (financierName instanceof List) {
                financier.setText(String.valueOf(((List) financierName).get(1)));
                d = (Double) ((List) financierName).get(0);
                iFinancierId = d.intValue();
                strFinancierId = String.valueOf(iFinancierId);
            }

            if (bookingResponse.getFinanceType().equalsIgnoreCase("in")) {
                financeType.setText("In-House");
                strFinanceTypeId = "in";
            } else {
                financeType.setText("Out-House");
                strFinanceTypeId = "out";
            }

            if (bookingResponse.getPriority().equalsIgnoreCase("1")) {
                priority.setText("Low");
                priority.setTextColor(getResources().getColor(R.color.chrome_green));
            } else if (bookingResponse.getPriority().equalsIgnoreCase("2")) {
                priority.setText("Medium");
                priority.setTextColor(getResources().getColor(R.color.chrome_yellow));
            } else if (bookingResponse.getPriority().equalsIgnoreCase("3")) {
                priority.setText("High");
                priority.setTextColor(getResources().getColor(R.color.chrome_red));
            }


            financeAmount.setText(String.valueOf(bookingResponse.getFinancePmt())/* + " \u20B9"*/);
            financePaymentDate.setText(bookingResponse.getFinancePaymentDate());
            marginMoneyAmount.setText(String.valueOf(bookingResponse.getMarginPmt())/* + " \u20B9"*/);
            marginMoneyDate.setText(bookingResponse.getMarginPaymentDate());
            deliveryDate.setText(bookingResponse.getDeliveryDate());
            allotted.setText(bookingResponse.getStockStatus());
            balAmount.setText(String.valueOf(bookingResponse.getBalanceAmount()) /*+ " \u20B9"*/);
            bookingAmount.setText(String.valueOf(bookingResponse.getBookingAmt()) /*+ " \u20B9"*/);
            bookingDate.setText(bookingResponse.getDob());
        }

    }

    @Override
    public void onSuccessFinanceType(final ArrayList<HashMap<String, String>> list) {
        financeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Select a finance type");

                final ArrayList<String> financeTypeList = new ArrayList<String>();

                for (int i = 0; i < list.size(); i++) {
                    financeTypeList.add(list.get(i).get(KEY_NAME));
                }

                final CharSequence[] reason = financeTypeList.toArray(new String[financeTypeList.size()]);

                builder.setItems(reason, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        financeType.setText(financeTypeList.get(pos));
                        strFinanceTypeId = list.get(pos).get(KEY_ID);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    @Override
    public void onSuccessFinancier(final Response response) {
        financier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<Record> list = response.getRecords();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Select a financier");

                final ArrayList<String> financierList = new ArrayList<String>();

                for (int i = 0; i < list.size(); i++) {
                    financierList.add(list.get(i).getName());
                }

                final CharSequence[] reason = financierList.toArray(new String[financierList.size()]);

                builder.setItems(reason, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        financier.setText(financierList.get(pos));
                        strFinancierId = String.valueOf(list.get(pos).getId());
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @OnClick(R.id.finance_payment_date) //ButterKnife uses.
    public void selectPaymentDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                financePaymentDateCalendar.set(Calendar.YEAR, year);
                financePaymentDateCalendar.set(Calendar.MONTH, monthOfYear);
                financePaymentDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(financePaymentDateCalendar, financePaymentDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, financePaymentDateCalendar
                .get(Calendar.YEAR), financePaymentDateCalendar.get(Calendar.MONTH),
                financePaymentDateCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.delivery_date) //ButterKnife uses.
    public void selectDeliveryDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                deliveryDateCalendar.set(Calendar.YEAR, year);
                deliveryDateCalendar.set(Calendar.MONTH, monthOfYear);
                deliveryDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(deliveryDateCalendar, deliveryDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, deliveryDateCalendar
                .get(Calendar.YEAR), deliveryDateCalendar.get(Calendar.MONTH),
                deliveryDateCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.margin_money_date) //ButterKnife uses.
    public void selectMarginMoneyDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                marginMoneyDateCalendar.set(Calendar.YEAR, year);
                marginMoneyDateCalendar.set(Calendar.MONTH, monthOfYear);
                marginMoneyDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePicker(marginMoneyDateCalendar, marginMoneyDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, marginMoneyDateCalendar
                .get(Calendar.YEAR), marginMoneyDateCalendar.get(Calendar.MONTH),
                marginMoneyDateCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private void timePicker(final Calendar myCalendar, final TextView txtView) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
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

    @OnClick(R.id.update) //ButterKnife uses.
    public void updateBookingDetails() {
        String sDeliveryDate = deliveryDate.getText().toString();
        String sFinancePaymentDate = financePaymentDate.getText().toString();
        String sFinanceAmount = financeAmount.getText().toString();
        String sMarginMoneyAmount = marginMoneyAmount.getText().toString();
        String sMarginMoneyDate = marginMoneyDate.getText().toString();

        presenter.updateBookingDetails(activity, strSaleOrderId, strFinanceTypeId, strFinancierId, sFinanceAmount,
                sFinancePaymentDate, sMarginMoneyAmount, sMarginMoneyDate, sDeliveryDate);
    }

    @Override
    public void onSuccessUpdateBookingDetails(CommonResponse response) {
        if (TextUtils.isEmpty(response.getError())) {
            DMSToast.showLong(activity, "Update Successfully");
            presenter.getBookingDetails(activity, strSaleOrderId);
        } else {
            DMSToast.showLong(activity, "Failed to update");
        }
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

}
