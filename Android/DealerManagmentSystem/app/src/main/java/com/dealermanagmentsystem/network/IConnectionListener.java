package com.dealermanagmentsystem.network;

public interface IConnectionListener {

    void onSuccess(Result result);

    void onFail(Result message);

    void onNetworkFail(String message);
}
