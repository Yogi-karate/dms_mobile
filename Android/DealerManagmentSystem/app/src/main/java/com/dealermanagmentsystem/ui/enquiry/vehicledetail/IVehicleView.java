package com.dealermanagmentsystem.ui.enquiry.vehicledetail;


import com.dealermanagmentsystem.data.model.common.Response;

public interface IVehicleView {

    public void onSuccessProducts(Response response);
    public void onSuccessVariants(Response response);
    public void onSuccessColors(Response response);
    public void onError(String message);


}
