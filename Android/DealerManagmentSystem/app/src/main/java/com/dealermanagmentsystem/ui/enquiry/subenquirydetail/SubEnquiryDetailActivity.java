package com.dealermanagmentsystem.ui.enquiry.subenquirydetail;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermanagmentsystem.R;
import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Record;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.subenquirydetail.SubEnquiryDetailResponse;
import com.dealermanagmentsystem.event.LeadActionMoveEvent;
import com.dealermanagmentsystem.ui.base.BaseActivity;
import com.dealermanagmentsystem.ui.base.BaseApplication;
import com.dealermanagmentsystem.ui.enquiry.enquirycreate.CreateEnquiryActivity;
import com.dealermanagmentsystem.ui.enquiry.tasks.TasksActivity;
import com.dealermanagmentsystem.ui.quotation.QuotationCreateActivity;
import com.dealermanagmentsystem.ui.saleorder.SaleOrderActivity;
import com.dealermanagmentsystem.ui.stock.StockPresenter;
import com.dealermanagmentsystem.utils.Utils;
import com.dealermanagmentsystem.utils.ui.DMSToast;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dealermanagmentsystem.constants.Constants.EXTRA_ACTIVITY_COMING_FROM;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_EMAIL;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_ENQUIRY_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_FOLLOWUP_DATE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_LEAD_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_MOBILE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_MODEL_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_PARTNER_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_QUOTATION_COUNT;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_SALE_TYPE_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_TEAM_NAME;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_ID;
import static com.dealermanagmentsystem.constants.Constants.EXTRA_USER_NAME;
import static com.dealermanagmentsystem.constants.Constants.LEAD_EDIT_ENQUIRY;
import static com.dealermanagmentsystem.constants.Constants.QUOTATION;

public class SubEnquiryDetailActivity extends BaseActivity implements ISubEnquiryDetailView {

    Activity activity;
    //String strPartnerName, strMobile, strEmail, strName, strTeamName, strUserName, strFollowUpDate;
    //int id, strTeamId, strUserId, quotationCount;

    int id;
    int strTeamId = 0;
    String strTeamName = "";
    int strUserId = 0;
    String strUserName = "";
    String strPartnerName;
    String strMobile;
    String strModel;
    String strModelId;
    String strStage;
    String strVariant;
    String strColor;
    String strSource;
    String strEmail;

    @BindView(R.id.create_quotation)
    Button btnCreateQuotation;
    @BindView(R.id.edit_enquiry)
    ImageView editEnquiry;

    @BindView(R.id.cold)
    ImageView cold;
    @BindView(R.id.warm)
    ImageView warm;
    @BindView(R.id.hot)
    ImageView hot;
    @BindView(R.id.booked)
    ImageView booked;

    @BindView(R.id.partner_detail)
    TextView txtPartnerDetail;
    @BindView(R.id.partner_number)
    TextView txtPartnerNumber;

    @BindView(R.id.sales_name)
    TextView txtSalesName;
    @BindView(R.id.team_name)
    TextView txtTeamName;
    @BindView(R.id.follow_up_date)
    TextView txtFollowUpDate;
    @BindView(R.id.quotation_count)
    TextView txtQuotationCount;
    @BindView(R.id.model_name)
    TextView txtModelName;
    @BindView(R.id.variant_name)
    TextView txtVariantName;
    @BindView(R.id.color_name)
    TextView txtColorName;
    @BindView(R.id.source_name)
    TextView txtSourceName;

    @BindView(R.id.won)
    LinearLayout llWon;
    @BindView(R.id.lost)
    LinearLayout llLost;
    @BindView(R.id.tasks)
    LinearLayout llTasks;
    @BindView(R.id.move_to)
    LinearLayout llMoveTo;

    SubEnquiryDetailPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_enquiry_detail);
        ButterKnife.bind(this);
        activity = SubEnquiryDetailActivity.this;
        showTile("Details");
        setStatusBarColor(getResources().getColor(R.color.bg));
        showBackButton();

        presenter = new SubEnquiryDetailPresenter(this);

        final Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(EXTRA_ENQUIRY_ID, 0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getEnquiryDetailResponse(activity, String.valueOf(id));
    }

    @OnClick(R.id.edit_enquiry) //ButterKnife uses.
    public void goToEditEnquiry() {
        Intent intent = new Intent(activity, CreateEnquiryActivity.class);
        intent.putExtra(EXTRA_ENQUIRY, LEAD_EDIT_ENQUIRY);
        intent.putExtra(EXTRA_ENQUIRY_ID, String.valueOf(id));
        activity.startActivityForResult(intent, 2);
    }

    @OnClick(R.id.create_quotation) //ButterKnife uses.
    public void goToCreateQuotation() {
        Intent intent = new Intent(activity, QuotationCreateActivity.class);
        intent.putExtra(EXTRA_ENQUIRY_ID, id);
        intent.putExtra(EXTRA_TEAM_ID, strTeamId);
        intent.putExtra(EXTRA_USER_ID, strUserId);
        intent.putExtra(EXTRA_MOBILE, strMobile);
        intent.putExtra(EXTRA_EMAIL, strEmail);
        intent.putExtra(EXTRA_PARTNER_NAME, strPartnerName);
        intent.putExtra(EXTRA_MODEL_ID, strModelId);
        activity.startActivity(intent);
    }

    @OnClick(R.id.ll_quotation) //ButterKnife uses.
    public void goToQuotation() {
        Intent intent = new Intent(activity, SaleOrderActivity.class);
        intent.putExtra(EXTRA_SALE_TYPE, QUOTATION);
        intent.putExtra(EXTRA_SALE_TYPE_ID, "draft");
        intent.putExtra(EXTRA_LEAD_ID, String.valueOf(id));
        activity.startActivity(intent);
    }

    @OnClick(R.id.partner_number) //ButterKnife uses.
    public void callMobile() {
        Utils.callMobile(strMobile, activity);
    }



    @Override
    public void onSuccessResponse(SubEnquiryDetailResponse subEnquiryDetailResponse) {

        strEmail = subEnquiryDetailResponse.getRecords().get(0).getEnquiry().getPartnerEmail();
        final Object teamId = subEnquiryDetailResponse.getRecords().get(0).getTeamId();
        Double dTeam;
        if (teamId instanceof List) {
            dTeam = (Double) ((List) teamId).get(0);
            strTeamId = dTeam.intValue();
            strTeamName = String.valueOf(((List) teamId).get(1));
        }

        final Object userId = subEnquiryDetailResponse.getRecords().get(0).getUserId();
        Double dUser;
        if (userId instanceof List) {
            dUser = (Double) ((List) userId).get(0);
            strUserId = dUser.intValue();
            strUserName = String.valueOf(((List) userId).get(1));
        }

        final Object stageId = subEnquiryDetailResponse.getRecords().get(0).getStageId();
        if (stageId instanceof List) {
            strStage = String.valueOf(((List) stageId).get(1));
        }

        if (strStage.equalsIgnoreCase("booked")) {
            booked.setImageResource(R.drawable.circle_filled);
            cold.setImageResource(R.drawable.circle);
            hot.setImageResource(R.drawable.circle);
            warm.setImageResource(R.drawable.circle);
        } else if (strStage.equalsIgnoreCase("hot")) {
            booked.setImageResource(R.drawable.circle);
            cold.setImageResource(R.drawable.circle);
            hot.setImageResource(R.drawable.circle_filled);
            warm.setImageResource(R.drawable.circle);
        } else if (strStage.equalsIgnoreCase("warm")) {
            booked.setImageResource(R.drawable.circle);
            cold.setImageResource(R.drawable.circle);
            hot.setImageResource(R.drawable.circle);
            warm.setImageResource(R.drawable.circle_filled);
        } else if (strStage.equalsIgnoreCase("cold")) {
            booked.setImageResource(R.drawable.circle);
            cold.setImageResource(R.drawable.circle_filled);
            hot.setImageResource(R.drawable.circle);
            warm.setImageResource(R.drawable.circle);
        }

        final Object productId = subEnquiryDetailResponse.getRecords().get(0).getEnquiry().getProductId();
        Double dModel;
        if (productId instanceof List) {
            dModel = (Double) ((List) productId).get(0);
            strModelId = String.valueOf(dModel.intValue());
            strModel = String.valueOf(((List) productId).get(1));
        }

        final Object variantId = subEnquiryDetailResponse.getRecords().get(0).getEnquiry().getProductVariant();
        if (variantId instanceof List) {
            strVariant = String.valueOf(((List) variantId).get(1));
        }

        final Object colorId = subEnquiryDetailResponse.getRecords().get(0).getEnquiry().getProductColor();
        if (colorId instanceof List) {
            strColor = String.valueOf(((List) colorId).get(1));
        }


        final Object sourceId = subEnquiryDetailResponse.getRecords().get(0).getEnquiry().getSourceId();
        if (sourceId instanceof List) {
            strSource = String.valueOf(((List) sourceId).get(1));
        }

        strPartnerName = subEnquiryDetailResponse.getRecords().get(0).getPartnerName();
        strMobile = subEnquiryDetailResponse.getRecords().get(0).getMobile();

        txtPartnerDetail.setText(strPartnerName);
        txtPartnerNumber.setText(strMobile);
        txtSalesName.setText(strUserName);
        txtTeamName.setText(strTeamName);
        txtFollowUpDate.setText(subEnquiryDetailResponse.getRecords().get(0).getActivityDateDeadline());
        txtQuotationCount.setText(String.valueOf(subEnquiryDetailResponse.getRecords().get(0).getSaleNumber()));
        txtQuotationCount.setPaintFlags(txtQuotationCount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtModelName.setText(strModel);
        txtVariantName.setText(strVariant);
        txtColorName.setText(strColor);
        txtSourceName.setText(strSource);
    }

    @Override
    public void onError(String message) {
        DMSToast.showLong(activity, message);
    }

    @OnClick(R.id.tasks) //ButterKnife uses.
    public void goToTasks() {
        Intent intent = new Intent(activity, TasksActivity.class);
        intent.putExtra(EXTRA_LEAD_ID, String.valueOf(id));
        intent.putExtra(EXTRA_USER_ID, strUserId);
        intent.putExtra(EXTRA_ACTIVITY_COMING_FROM, "Leads");
        activity.startActivity(intent);
    }

    @OnClick(R.id.move_to) //ButterKnife uses.
    public void goMoveTo() {
        presenter.getStage(activity, String.valueOf(id));
    }

    @OnClick(R.id.won) //ButterKnife uses.
    public void goToWon() {
        presenter.markWonLost(activity, String.valueOf(id), "Won", 0);
    }

    @OnClick(R.id.lost) //ButterKnife uses.
    public void goToLost() {
        presenter.getLostReason(activity, String.valueOf(id));
    }

    @Override
    public void onSuccessWonLost(CommonResponse commonResponse) {
        if (commonResponse.getSuccess()) {
            DMSToast.showLong(activity, "Success");
            finish();
        } else if (!TextUtils.isEmpty(commonResponse.getError())) {
            onError("Could not submit response");
        }
    }

    @Override
    public void onSuccessLostReason(Response response, String id) {
        final List<Record> records = response.getRecords();
        if (records.isEmpty()) {
            onError("No lost reasons found, try after sometime");
        } else {
            showLostDialog(id, records);
        }
    }


    @Override
    public void onSuccessGetStages(Response response, String id) {
        final List<Record> records = response.getRecords();
        if (records.isEmpty()) {
            onError("No Stages found, try after sometime");
        } else {
            showStagesDialog(id, records);
        }
    }
    private void showLostDialog(final String id, final List<Record> records) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select a lost reason");

        ArrayList<String> reasonList = new ArrayList<String>();

        for (int i = 0; i < records.size(); i++) {
            reasonList.add(records.get(i).getName());
        }

        final CharSequence[] reason = reasonList.toArray(new String[reasonList.size()]);

        builder.setItems(reason, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                presenter.markWonLost(activity, id, "Lost", records.get(pos).getId());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showStagesDialog(final String leadId, final List<Record> records) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Move to different stage");

        ArrayList<String> stageList = new ArrayList<String>();

        for (int i = 0; i < records.size(); i++) {
            stageList.add(records.get(i).getName());
        }

        final CharSequence[] reason = stageList.toArray(new String[stageList.size()]);

        builder.setItems(reason, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                presenter.moveStage(activity, leadId, records.get(pos).getId());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}