package com.dealermanagmentsystem.ui.service.create;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;

import java.util.ArrayList;
import java.util.HashMap;

public interface IServiceCreateBookingView {

    public void onSuccessServiceCreateBooking(CommonResponse response);
    public void onSuccessBookingType(ArrayList<HashMap<String, String>> list);
    public void onSuccessServiceType(ArrayList<HashMap<String, String>> list);
    public void onSuccessServiceLocation(Response response);
    public void onError(String message);

}
