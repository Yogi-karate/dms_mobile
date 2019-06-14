package com.dealermanagmentsystem.ui.enquiry.enquirycreate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.enquirydetail.EnquiryDetailResponse;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquirydetail.EnquiryEditRequest;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.enquiry.financedetail.FinanceDetailActivity;
import com.dealermanagmentsystem.ui.enquiry.insurancedetail.InsuranceDetailActivity;
import com.dealermanagmentsystem.ui.enquiry.vehicledetail.VehicleDetailActivity;
import com.dealermanagmentsystem.utils.ui.DMSToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.CREATE_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;
import static com.dealermanagmentsystem.constants.Constants.LEAD_EDIT_ENQUIRY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.ENQUIRY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.LEAD_EDIT_ENQUIRIES;


public class CreateEnquiryActivity extends BaseActivity implements ICreateEnquireView {


    EnquiryCreatePresenter enquiryCreatePresenter;
    Activity activity;

    @BindView(R.id.et_types)
    EditText etType;
    @BindView(R.id.et_product)
    AutoCompleteTextView etProduct;
    @BindView(R.id.et_source)
    AutoCompleteTextView etSource;
    @BindView(R.id.et_follow_up_date)
    TextView etFollowUpDate;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile_no)
    EditText etMobileNo;
    @BindView(R.id.et_mail)
    EditText etMail;
    @BindView(R.id.ll_details_parent)
    CardView llDetailsParent;
    @BindView(R.id.txt_vehicle)
    TextView txtVehicle;
    @BindView(R.id.txt_finance)
    TextView txtFinance;
    @BindView(R.id.txt_insurance)
    TextView txtInsurance;
    @BindView(R.id.et_test_drive_date)
    TextView etTestDriveDate;
    @BindView(R.id.cb_test_drive)
    CheckBox cbTestDrive;

    //private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar, myCalendarDriveDate;
    ArrayList selectedItemsTypes = new ArrayList();
    List<Record> typeRecords = new ArrayList<>();
    List<Record> productRecords = new ArrayList<>();
    List<Record> sourceResponseRecords = new ArrayList<>();
    String stringExtra;
    public static EnquiryDetailResponse detailResponse;
    public static EnquiryEditRequest enquiryDetailRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_enquiry);
        ButterKnife.bind(this);
        activity = CreateEnquiryActivity.this;
        selectedItemsTypes = new ArrayList();

        setStatusBarColor(getResources().getColor(R.color.bg));
        myCalendar = Calendar.getInstance();
        myCalendarDriveDate = Calendar.getInstance();

        enquiryCreatePresenter = new EnquiryCreatePresenter(this);
        enquiryCreatePresenter.getTypes(activity);
        enquiryCreatePresenter.getProduct(activity);
        enquiryCreatePresenter.getSource(activity);


        final Intent intent = getIntent();
        if (intent != null) {
            stringExtra = intent.getStringExtra(EXTRA_ENQUIRY);
            if (stringExtra.equalsIgnoreCase(CREATE_ENQUIRY)) {
                showTile("Create Enquiry");
                showBackButton();
                llDetailsParent.setVisibility(View.GONE);
            } else {
                showTile("Enquiry Details");
                showBackButton();
                String url;
                if (LEAD_EDIT_ENQUIRY.equalsIgnoreCase(stringExtra)) {
                    url = LEAD_EDIT_ENQUIRIES + intent.getStringExtra(EXTRA_ENQUIRY_ID);//From leads edit enquiry
                } else {
                    url = ENQUIRY + "/" + intent.getStringExtra(EXTRA_ENQUIRY_ID);//From enquiry edit it
                }
                enquiryCreatePresenter.getEnquiryDetail(activity, url);
            }
        }
    }

    @OnClick(R.id.et_test_drive_date) //ButterKnife uses.
    public void selectTestDriveDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarDriveDate.set(Calendar.YEAR, year);
                myCalendarDriveDate.set(Calendar.MONTH, monthOfYear);
                myCalendarDriveDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";//In which you need put here "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                etTestDriveDate.setText(sdf.format(myCalendarDriveDate.getTime()));

            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendarDriveDate
                .get(Calendar.YEAR), myCalendarDriveDate.get(Calendar.MONTH),
                myCalendarDriveDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.et_follow_up_date) //ButterKnife uses.
    public void selectFollowUpDate() {
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
                etFollowUpDate.setText(sdf.format(myCalendar.getTime()));

            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onSuccessTypes(Response typesResponse) {
        final ArrayList<String> types = new ArrayList<>();
        typeRecords = typesResponse.getRecords();
        if (typeRecords != null) {
            for (int i = 0; i < typeRecords.size(); i++) {
                types.add(typeRecords.get(i).getName());
            }
        }

        if (stringExtra.equalsIgnoreCase(CREATE_ENQUIRY)) {
            etType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (types.isEmpty()) {
                        DMSToast.showLong(activity, "No types found, try after sometime");
                    } else {
                        typesDialog(types);
                    }

                }
            });
        }

    }

    public void typesDialog(final ArrayList<String> types) {
        String selectCategory = "Select the types";
        final String deleteOK = "OK";
        final String deleteCancel = "Cancel";
        final CharSequence[] items = types.toArray(new CharSequence[types.size()]);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(selectCategory)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            if (!selectedItemsTypes.contains(types.get(indexSelected))) {
                                selectedItemsTypes.add(types.get(indexSelected));
                            }
                        } else if (selectedItemsTypes.contains(types.get(indexSelected))) {
                            selectedItemsTypes.remove(types.get(indexSelected));
                        }
                    }
                }).setPositiveButton(deleteOK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (selectedItemsTypes.isEmpty()) {
                            DMSToast.showLong(mContext, "Please select a type");
                        } else {
                            etType.setText("");
                            dialog.dismiss();
                            for (int i = 0; i < selectedItemsTypes.size(); i++) {
                                etType.append(selectedItemsTypes.get(i) + " ");
                            }
                        }
                    }
                }).setNegativeButton(deleteCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onSuccessProduct(Response productResponse) {
        ArrayList<String> products = new ArrayList<>();
        productRecords = productResponse.getRecords();
        if (productRecords != null) {
            for (int i = 0; i < productRecords.size(); i++) {
                products.add(productRecords.get(i).getName());
            }

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, products);
        etProduct.setThreshold(1);
        etProduct.setAdapter(adapter);

    }

    @Override
    public void onSuccessSource(Response sourceResponse) {
        ArrayList<String> sources = new ArrayList<>();
        sourceResponseRecords = sourceResponse.getRecords();
        if (sourceResponseRecords != null) {
            for (int i = 0; i < sourceResponseRecords.size(); i++) {
                sources.add(sourceResponseRecords.get(i).getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, sources);

        etSource.setThreshold(1);//will start working from first character
        etSource.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //etSource.showDropDown();
    }

    @OnClick(R.id.submit_enquiry) //ButterKnife uses.
    public void submitEnquiry() {
        //Fetch TypeId List
        List<Integer> typeListId = new ArrayList<>();
        for (int i = 0; i < selectedItemsTypes.size(); i++) {
            for (int j = 0; j < typeRecords.size(); j++) {
                if (selectedItemsTypes.get(i).equals(typeRecords.get(j).getName())) {
                    typeListId.add(typeRecords.get(j).getId());
                }
            }
        }

        //Fetch Product Id
        final String strProductName = etProduct.getText().toString();
        int productId = -1;
        for (int i = 0; i < productRecords.size(); i++) {
            if (strProductName.equalsIgnoreCase(productRecords.get(i).getName())) {
                productId = productRecords.get(i).getId();
            }
        }

        //Fetch Source ID
        String strSourceName = etSource.getText().toString();
        int sourceId = -1;
        for (int i = 0; i < sourceResponseRecords.size(); i++) {
            if (strSourceName.equalsIgnoreCase(sourceResponseRecords.get(i).getName())) {
                sourceId = sourceResponseRecords.get(i).getId();
            }
        }

        //fetch partner details
        final String strFollowUpDate = etFollowUpDate.getText().toString();
        final String strName = etName.getText().toString();
        final String strMobileNo = etMobileNo.getText().toString();
        final String strMail = etMail.getText().toString();
        final String strDriveDate = etTestDriveDate.getText().toString();
        final boolean driveDate = cbTestDrive.isChecked();

        if (stringExtra.equalsIgnoreCase(CREATE_ENQUIRY)) {
            enquiryCreatePresenter.createEnquiry(activity, typeListId, productId, sourceId,
                    strFollowUpDate, strName, strMobileNo, strMail, strDriveDate, driveDate);
        } else {

            if (!strDriveDate.equalsIgnoreCase(detailResponse.getTestDriveDate())) {
                detailResponse.setTestDriveDate(strDriveDate);
                enquiryDetailRequest.setTestDriveDate(strDriveDate);
            }

            if (driveDate != detailResponse.getTestDrive()) {
                detailResponse.setTestDrive(driveDate);
                enquiryDetailRequest.setTestDrive(driveDate);
            }

            if (!strFollowUpDate.equalsIgnoreCase(detailResponse.getDateFollowUp())) {
                detailResponse.setDateFollowUp(strFollowUpDate);
                enquiryDetailRequest.setDateFollowUp(strFollowUpDate);
            }

            if (!strName.equalsIgnoreCase(detailResponse.getPartnerName())) {
                detailResponse.setPartnerName(strName);
                enquiryDetailRequest.setPartnerName(strName);
            }

            if (!strMobileNo.equalsIgnoreCase(detailResponse.getPartnerMobile())) {
                detailResponse.setPartnerMobile(strMobileNo);
                enquiryDetailRequest.setPartnerMobile(strMobileNo);
            }

            if (!strMail.equalsIgnoreCase(detailResponse.getPartnerEmail())) {
                detailResponse.setPartnerEmail(strMail);
                enquiryDetailRequest.setPartnerEmail(strMail);
            }

            if (!String.valueOf(productId).equalsIgnoreCase(String.valueOf(detailResponse.getProductId().get(0)))) {
                List<Object> objectList = new ArrayList<>();
                objectList.add(productId);
                objectList.add(strProductName);
                detailResponse.setProductId(objectList);
                enquiryDetailRequest.setProductId(productId);
            }

            if (!String.valueOf(sourceId).equalsIgnoreCase(String.valueOf(detailResponse.getSourceId().get(0)))) {
                List<Object> objectList = new ArrayList<>();
                objectList.add(sourceId);
                objectList.add(strSourceName);
                detailResponse.setSourceId(objectList);
                enquiryDetailRequest.setSourceId(sourceId);
            }

            enquiryCreatePresenter.editEnquiry(activity, enquiryDetailRequest, detailResponse);
        }

    }


    @Override
    public void onSuccessEnquiryDetail(EnquiryDetailResponse enquiryDetailResponse) {
        enquiryDetailRequest = new EnquiryEditRequest();
        detailResponse = new EnquiryDetailResponse();
        detailResponse = enquiryDetailResponse;

        final List<Integer> typeIds = enquiryDetailResponse.getTypeIds();
        if (typeIds.isEmpty()) {
            llDetailsParent.setVisibility(View.GONE);
        }

        for (int j = 0; j < typeIds.size(); j++) {
            for (int i = 0; i < typeRecords.size(); i++) {
                if (typeRecords.get(i).getId() == typeIds.get(j)) {
                    etType.append(typeRecords.get(i).getName() + " ");
                }
            }

            if (typeIds.get(j) == 1) {
                txtVehicle.setVisibility(View.VISIBLE);
            } else if (typeIds.get(j) == 2) {
                txtFinance.setVisibility(View.VISIBLE);
            } else if (typeIds.get(j) == 3) {
                txtInsurance.setVisibility(View.VISIBLE);
            }
        }

        etTestDriveDate.setText(enquiryDetailResponse.getTestDriveDate());
        cbTestDrive.setChecked(enquiryDetailResponse.getTestDrive());
        etFollowUpDate.setText(enquiryDetailResponse.getDateFollowUp());
        etName.setText(enquiryDetailResponse.getPartnerName());
        etMobileNo.setText(enquiryDetailResponse.getPartnerMobile());
        etMail.setText(enquiryDetailResponse.getPartnerEmail());
        etSource.setText(enquiryDetailResponse.getSourceId().get(1).toString());
        etProduct.setText(enquiryDetailResponse.getProductId().get(1).toString());

    }

    @Override
    public void onSuccessCreateEnquiry(String id) {
        successDialog(id);
    }

    public void successDialog(String id) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_enquiry_sucess, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        final Button ok = view.findViewById(R.id.ok);
        final TextView name = view.findViewById(R.id.name);
        name.setText("Enquiry id is " + id);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(2, intent);
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

    @Override
    public void onSuccessEditEnquiryDetail(String message) {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
        DMSToast.showLong(activity, "Enquiry edited successfully");
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }


    @OnClick(R.id.txt_vehicle) //ButterKnife uses.
    public void launhVehicle() {
        //Fetch Product Id
        final String strProductName = etProduct.getText().toString();
        int productId = -1;
        for (int i = 0; i < productRecords.size(); i++) {
            if (strProductName.equalsIgnoreCase(productRecords.get(i).getName())) {
                productId = productRecords.get(i).getId();
            }
        }

        List<Object> objectList = new ArrayList<>();
        objectList.add(productId);
        objectList.add(strProductName);
        detailResponse.setProductId(objectList);
        enquiryDetailRequest.setProductId(productId);

        Intent intent = new Intent(this, VehicleDetailActivity.class);
        startActivityForResult(intent, 3);
    }

    @OnClick(R.id.txt_finance) //ButterKnife uses.
    public void launhFinance() {
        Intent intent = new Intent(this, FinanceDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.txt_insurance) //ButterKnife uses.
    public void launhInsurance() {
        Intent intent = new Intent(this, InsuranceDetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            etProduct.setText(detailResponse.getProductId().get(1).toString());
        }
    }

}
