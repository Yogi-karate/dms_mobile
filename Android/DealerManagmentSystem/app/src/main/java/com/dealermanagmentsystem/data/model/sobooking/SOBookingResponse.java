
package com.dealermanagmentsystem.data.model.sobooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SOBookingResponse {

    @SerializedName("booking_amt")
    @Expose
    private Double bookingAmt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("margin_pmt")
    @Expose
    private Double marginPmt;
    @SerializedName("balance_amount")
    @Expose
    private Double balanceAmount;
    @SerializedName("margin_payment_date")
    @Expose
    private String marginPaymentDate;
    @SerializedName("financier_name")
    @Expose
    private Object financierName;
    @SerializedName("stock_status")
    @Expose
    private String stockStatus;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("finance_pmt")
    @Expose
    private Double financePmt;
    @SerializedName("finance_payment_date")
    @Expose
    private String financePaymentDate;
    @SerializedName("finance_type")
    @Expose
    private String financeType;
    @SerializedName("priority")
    @Expose
    private String priority;

    public Double getBookingAmt() {
        return bookingAmt;
    }

    public void setBookingAmt(Double bookingAmt) {
        this.bookingAmt = bookingAmt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getMarginPmt() {
        return marginPmt;
    }

    public void setMarginPmt(Double marginPmt) {
        this.marginPmt = marginPmt;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getMarginPaymentDate() {
        return marginPaymentDate;
    }

    public void setMarginPaymentDate(String marginPaymentDate) {
        this.marginPaymentDate = marginPaymentDate;
    }

    public Object getFinancierName() {
        return financierName;
    }

    public void setFinancierName(Object financierName) {
        this.financierName = financierName;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Double getFinancePmt() {
        return financePmt;
    }

    public void setFinancePmt(Double financePmt) {
        this.financePmt = financePmt;
    }

    public String getFinancePaymentDate() {
        return financePaymentDate;
    }

    public void setFinancePaymentDate(String financePaymentDate) {
        this.financePaymentDate = financePaymentDate;
    }

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
