
package com.dealermanagmentsystem.data.model.sopayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("communication")
    @Expose
    private String communication;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

}
