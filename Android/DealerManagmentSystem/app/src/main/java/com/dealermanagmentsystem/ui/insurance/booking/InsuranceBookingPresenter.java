package com.dealermanagmentsystem.ui.insurance.booking;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.bookinginsurance.InsuranceBookingResponse;
import com.dealermanagmentsystem.data.model.bookingservice.ServiceBookingResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.ui.enquiry.insurancedetail.IInsurancePresenter;
import com.dealermanagmentsystem.ui.service.booking.IServiceBookingPresenter;
import com.dealermanagmentsystem.ui.service.booking.IServiceBookingView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.INSURANCE_BOOKING;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SERVICE_BOOKING;

public class InsuranceBookingPresenter implements IInsuranceBookingPresenter {

    IInsuranceBookingView view;

    public InsuranceBookingPresenter(IInsuranceBookingView insuranceBookingView) {
        view = insuranceBookingView;
    }

    @Override
    public void getInsuranceBookings(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(INSURANCE_BOOKING, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    InsuranceBookingResponse insuranceBookingResponse = gson.fromJson(jsonObject.toString(), InsuranceBookingResponse.class);
                    view.onSuccessInsuranceBooking(insuranceBookingResponse);
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
