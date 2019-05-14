package com.dealermanagmentsystem.ui.enquiry.vehicledetail;

import android.app.Activity;


public interface IVehiclePresenter {

    public void getVariants(Activity activity, int id);

    public void getProduct(Activity activity);

    public void getColors(Activity activity, int strProductId, int strVariantId);


}
