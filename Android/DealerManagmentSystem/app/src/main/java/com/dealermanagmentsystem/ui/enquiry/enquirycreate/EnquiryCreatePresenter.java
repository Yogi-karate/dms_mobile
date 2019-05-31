package com.dealermanagmentsystem.ui.enquiry.enquirycreate;

import android.app.Activity;
import android.text.TextUtils;

import com.dealermanagmentsystem.data.model.createenquiry.EnquireCreateRequest;
import com.dealermanagmentsystem.data.model.enquirydetail.EnquiryDetailResponse;
import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.data.model.enquirydetail.EnquiryEditRequest;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.POST;
import static com.dealermanagmentsystem.constants.ConstantsUrl.ENQUIRY;
import static com.dealermanagmentsystem.constants.ConstantsUrl.PRODUCT;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SOURCE;
import static com.dealermanagmentsystem.constants.ConstantsUrl.TYPES;

public class EnquiryCreatePresenter implements IEnquiryCreatePresenter {

    ICreateEnquireView view;

    public EnquiryCreatePresenter(ICreateEnquireView iCreateEnquireView) {
        view = iCreateEnquireView;
    }

    @Override
    public void getTypes(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(TYPES, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response typesResponse = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessTypes(typesResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void getProduct(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(PRODUCT, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response typesResponse = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessProduct(typesResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void getSource(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SOURCE, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    Response typesResponse = gson.fromJson(jsonObject.toString(), Response.class);
                    view.onSuccessSource(typesResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void createEnquiry(final Activity activity, List<Integer> typeListId, int productId,
                              int sourceId, String strFollowUpDate, String strName,
                              String strMobileNo, String strMail, String strDriveDate, boolean driveDate) {

        if (typeListId.isEmpty()) {
            view.onError("please select a type");
        } else if (productId == -1) {
            view.onError("please enter a product");
        } else if (TextUtils.isEmpty(strFollowUpDate)) {
            view.onError("please enter a follow up date");
        } else if (TextUtils.isEmpty(strName)) {
            view.onError("please enter a Name");
        } else if (TextUtils.isEmpty(strMobileNo)) {
            view.onError("please enter a mobile no");
        } else if (strMobileNo.length() != 10) {
            view.onError("please enter a valid 10 digit mobile no");
        } else if (sourceId == -1) {
            view.onError("please enter a source");
        } else if (!TextUtils.isEmpty(strDriveDate) && !driveDate) {
            view.onError("please select the checkbox for test drive");
        } else {
            EnquireCreateRequest enquireCreateRequest = new EnquireCreateRequest();

            List<Object> typeIdListParent = new ArrayList<>();
            typeIdListParent.add(6);
            typeIdListParent.add(0);
            typeIdListParent.add(typeListId);

            List<Object> typeIdLis1 = new ArrayList<>();
            typeIdLis1.add(typeIdListParent);
            enquireCreateRequest.setTypeIds(typeIdLis1);

            enquireCreateRequest.setProductId(productId);
            enquireCreateRequest.setDateFollowUp(strFollowUpDate);
            enquireCreateRequest.setPartnerName(strName);
            enquireCreateRequest.setPartnerMobile(strMobileNo);
            enquireCreateRequest.setPartnerEmail(strMail);
            enquireCreateRequest.setSourceId(sourceId);
            enquireCreateRequest.setTestDrive(driveDate);
            if (!TextUtils.isEmpty(strDriveDate)) {
                enquireCreateRequest.setTestDriveDate(strDriveDate);
            }

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(enquireCreateRequest);

            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(ENQUIRY, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    view.onSuccessCreateEnquiry();
                }

                @Override
                public void onFail(Result result) {
                    view.onError("Something went wrong, Please try after sometime");
                }

                @Override
                public void onNetworkFail(String message) {
                    view.onError(message);
                }
            });
            asyncTaskConnection.execute();
        }

    }

    @Override
    public void getEnquiryDetail(Activity activity, String url) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(url, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    EnquiryDetailResponse enquiryDetailResponse = gson.fromJson(jsonObject.toString(), EnquiryDetailResponse.class);
                    view.onSuccessEnquiryDetail(enquiryDetailResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }

    @Override
    public void editEnquiry(Activity activity, EnquiryEditRequest enquiryDetailResponse, EnquiryDetailResponse detailResponse) {
        //enquiryDetailRequest this is request that i am sending
        //detailResponse this is reponse that i have received
        final String partnerMobile = detailResponse.getPartnerMobile();
        final int productId = (int) detailResponse.getProductId().get(0);
        final int sourceId = (int) detailResponse.getSourceId().get(0);
        if (productId == -1) {
            view.onError("please enter a product");
        } else if (TextUtils.isEmpty(detailResponse.getDateFollowUp())) {
            view.onError("please enter a follow up date");
        } else if (TextUtils.isEmpty(detailResponse.getPartnerName())) {
            view.onError("please enter a Name");
        } else if (TextUtils.isEmpty(partnerMobile)) {
            view.onError("please enter a mobile no");
        } else if (partnerMobile.length() != 10) {
            view.onError("please enter a valid 10 digit mobile no");
        } else if (sourceId == -1) {
            view.onError("please enter a source");
        } else if (!TextUtils.isEmpty(detailResponse.getTestDriveDate()) && !detailResponse.getTestDrive()) {
            view.onError("please select the checkbox for test drive");
        }/*else if (Utils.isValidEmail(enquiryDetailResponse.getPartnerEmail())) {
            view.onError("please enter a valid email");
        } */ else {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(enquiryDetailResponse);

            final String id = String.valueOf(detailResponse.getId());
            AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(ENQUIRY + "/" + id, activity, json, POST, new IConnectionListener() {
                @Override
                public void onSuccess(Result result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result.getResponse());
                        Gson gson = new Gson();
                        view.onSuccessEditEnquiryDetail(result.getResponse());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(Result result) {
                    view.onError("Something went wrong, Please try after sometime");
                }

                @Override
                public void onNetworkFail(String message) {
                    view.onError(message);
                }
            });
            asyncTaskConnection.execute();
        }


    }
}
