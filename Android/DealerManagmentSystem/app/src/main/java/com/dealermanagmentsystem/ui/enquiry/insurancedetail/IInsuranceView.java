package com.dealermanagmentsystem.ui.enquiry.insurancedetail;



import java.util.ArrayList;
import java.util.HashMap;

public interface IInsuranceView {

    public void onSuccessInsurancePunch(ArrayList<HashMap<String, String>> list);
    public void onSuccessInsuranceType(ArrayList<HashMap<String, String>> list);
    public void onError(String message);


}
