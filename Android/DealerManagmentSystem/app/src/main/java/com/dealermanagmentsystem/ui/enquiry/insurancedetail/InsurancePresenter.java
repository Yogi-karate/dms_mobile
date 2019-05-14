package com.dealermanagmentsystem.ui.enquiry.insurancedetail;

import android.app.Activity;

import com.dealermanagmentsystem.data.model.common.Response;
import com.dealermanagmentsystem.network.AsyncTaskConnection;
import com.dealermanagmentsystem.network.IConnectionListener;
import com.dealermanagmentsystem.network.Result;
import com.dealermanagmentsystem.ui.enquiry.financedetail.IFinancePresenter;
import com.dealermanagmentsystem.ui.enquiry.financedetail.IFinanceView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.dealermanagmentsystem.constants.Constants.GET;
import static com.dealermanagmentsystem.constants.Constants.KEY_ID;
import static com.dealermanagmentsystem.constants.Constants.KEY_NAME;
import static com.dealermanagmentsystem.constants.ConstantsUrl.FINANCIER;

public class InsurancePresenter implements IInsurancePresenter {

    IInsuranceView view;

    public InsurancePresenter(IInsuranceView iInsuranceView) {
        view = iInsuranceView;
    }

    @Override
    public void getInsurancePunch(Activity activity) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(KEY_NAME, "HAP");
        hashMap.put(KEY_ID, "hap");
        list.add(hashMap);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put(KEY_NAME, "NON-HAP");
        hashMap1.put(KEY_ID, "nonhap");
        list.add(hashMap1);

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put(KEY_NAME, "Covernote");
        hashMap2.put(KEY_ID, "covernote");
        list.add(hashMap2);

        view.onSuccessInsurancePunch(list);
    }

    @Override
    public void getInsuranceType(Activity activity) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(KEY_NAME, "In-House");
        hashMap.put(KEY_ID, "in");
        list.add(hashMap);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put(KEY_NAME, "Out-House");
        hashMap1.put(KEY_ID, "out");
        list.add(hashMap1);

        view.onSuccessInsuranceType(list);
    }
}
