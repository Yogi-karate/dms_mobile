
package com.dealermanagmentsystem.data.model.payment;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record implements Serializable {

    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("partner_id")
    @Expose
    private Object partnerId = null;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("id")
    @Expose
    private Integer id;

    protected Record(Parcel in) {
        createDate = in.readString();
        paymentType = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readInt();
        }
        paymentDate = in.readString();
        state = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
    }



    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Object partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
