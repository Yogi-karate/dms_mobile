package com.dealermanagmentsystem.ui.enquiry.financedetail;


import com.dealermanagmentsystem.data.model.common.Response;

import java.util.ArrayList;
import java.util.HashMap;

public interface IFinanceView {

    public void onSuccessFinancier(Response enquiryResponse);
    public void onSuccessFinanceType(ArrayList<HashMap<String, String>> list);
    public void onError(String message);


}
