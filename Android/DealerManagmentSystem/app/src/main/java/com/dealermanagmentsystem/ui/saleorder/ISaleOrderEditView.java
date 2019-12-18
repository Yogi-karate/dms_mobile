package com.dealermanagmentsystem.ui.saleorder;


import com.dealermanagmentsystem.data.model.common.CommonResponse;

public interface ISaleOrderEditView {

    public void onSuccessSaleOrderEdit(CommonResponse response);
    public void onErrorMessage(String message);

}
