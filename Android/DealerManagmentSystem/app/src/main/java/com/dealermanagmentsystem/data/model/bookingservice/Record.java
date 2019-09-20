
package com.dealermanagmentsystem.data.model.bookingservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("vehicle_model")
    @Expose
    private String vehicleModel;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;
    @SerializedName("booking_type")
    @Expose
    private String bookingType;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dop")
    @Expose
    private String dop;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("user_id")
    @Expose
    private Object userId = null;
    @SerializedName("location_id")
    @Expose
    private Object locationId = null;

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDop() {
        return dop;
    }

    public void setDop(String dop) {
        this.dop = dop;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getLocationId() {
        return locationId;
    }

    public void setLocationId(Object locationId) {
        this.locationId = locationId;
    }

}
