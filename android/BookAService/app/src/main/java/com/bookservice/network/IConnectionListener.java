package com.bookservice.network;

public interface IConnectionListener {

    void onSuccess(Result result);

    void onFail(Result message);
}
