package com.dealermanagmentsystem.ui.login;


import com.dealermanagmentsystem.data.model.endpoint.EndPointResponse;
import com.dealermanagmentsystem.data.model.login.LoginResponse;

public interface ILoginView {

    public void onSuccessLogin(LoginResponse message);

    public void onSuccessEndPoint(EndPointResponse message);

    public void onError(String message);


}
