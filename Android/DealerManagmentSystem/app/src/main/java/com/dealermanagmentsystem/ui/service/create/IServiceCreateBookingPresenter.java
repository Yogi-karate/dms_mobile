package com.dealermanagmentsystem.ui.service.create;

import android.app.Activity;

public interface IServiceCreateBookingPresenter {

    public void createServiceBooking(Activity activity, int locationId, String strBookingTypeId, String strDop, String strAddress, String strRemarks, String strLeadId, String strServiceTypeId);

    public void getBookingType();

    public void getServiceType();

    public void getLocation(Activity activity);

}
