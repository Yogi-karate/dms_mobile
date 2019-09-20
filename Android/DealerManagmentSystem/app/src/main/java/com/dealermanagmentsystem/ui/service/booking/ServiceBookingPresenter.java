package com.dealermanagmentsystem.ui.service.booking;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.bookingservice.ServiceBookingResponse;
import com.dealermanagmentsystem.data.model.enquiry.EnquiryResponse;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.ConstantsUrl.SERVICE_BOOKING;

public class ServiceBookingPresenter implements IServiceBookingPresenter {

    IServiceBookingView view;

    public ServiceBookingPresenter(IServiceBookingView iServiceBookingView) {
        view = iServiceBookingView;
    }

    @Override
    public void getServiceBookings(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(SERVICE_BOOKING, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    ServiceBookingResponse serviceBookingResponse = gson.fromJson(jsonObject.toString(), ServiceBookingResponse.class);
                    view.onSuccessServiceBooking(serviceBookingResponse);
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
