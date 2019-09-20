package com.dealermanagmentsystem.ui.users;


import com.dealermanagmentsystem.data.model.loadusers.LoadUserResponse;

public interface IUserView {

    public void onSuccessUserList(LoadUserResponse enquiryResponse);
    public void onError(String message);


}
