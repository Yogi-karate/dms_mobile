package com.dealermanagmentsystem.ui.delivery;


import com.dealermanagmentsystem.data.model.delivery.DeliveryResponse;

public interface IDeliveryView {

    public void onSuccessDelivery(DeliveryResponse deliveryResponse);
    public void onError(String message);


}
