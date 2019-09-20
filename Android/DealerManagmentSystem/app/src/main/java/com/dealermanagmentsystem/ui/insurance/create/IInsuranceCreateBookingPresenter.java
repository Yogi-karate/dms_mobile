package com.dealermanagmentsystem.ui.insurance.create;

import android.app.Activity;

public interface IInsuranceCreateBookingPresenter {

    public void createInsuranceBooking(Activity activity, String leadId, int previousCompanyId, String strPreviousNilDip, String strPreviousIDV, String strPreviousNCB, String strDiscount, String strPreviousDueDate, String strPreviousFinalPremium, String strPreviousPolicyNo, int currentCompanyId, String strCurrentNilDip, String strBookingTypeId, String strCurrentIDV, String strCurrentNCB, String strAlternateNumber, String strCurrentDueDate, String strCurrentFinalPremium, String strPickUpDate, String strLeadId);

    public void getBookingType();

    public void getNilDip();

    public void getBanks(Activity activity);

}
