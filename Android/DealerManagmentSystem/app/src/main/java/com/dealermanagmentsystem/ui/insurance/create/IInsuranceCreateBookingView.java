package com.dealermanagmentsystem.ui.insurance.create;


import com.dealermanagmentsystem.data.model.common.CommonResponse;
import com.dealermanagmentsystem.data.model.common.Response;

import java.util.ArrayList;
import java.util.HashMap;

public interface IInsuranceCreateBookingView {

    public void onSuccessInsuranceCreateBooking(CommonResponse response);
    public void onSuccessBookingType(ArrayList<HashMap<String, String>> list);
    public void onSuccessNilDipType(ArrayList<HashMap<String, String>> list);
    public void onSuccessBank(Response response);
    public void onError(String message);

}
