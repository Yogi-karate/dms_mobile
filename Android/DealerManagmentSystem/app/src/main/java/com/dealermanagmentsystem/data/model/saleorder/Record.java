
package com.dealermanagmentsystem.data.model.saleorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("finance_type")
    @Expose
    private String financeType;
    @SerializedName("financier_name")
    @Expose
    private Object financierName;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("product_variant")
    @Expose
    private String productVariant;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("balance_amount")
    @Expose
    private Double balanceAmount;
    @SerializedName("product_color")
    @Expose
    private String productColor;
    @SerializedName("stock_status")
    @Expose
    private String stockStatus;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;

    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("partner_id")
    @Expose
    private Object partnerId;
    @SerializedName("booking_amt")
    @Expose
    private Double bookingAmt;
    @SerializedName("team_id")
    @Expose
    private Object teamId;

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

    public Object getFinancierName() {
        return financierName;
    }

    public void setFinancierName(Object financierName) {
        this.financierName = financierName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(String productVariant) {
        this.productVariant = productVariant;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Object partnerId) {
        this.partnerId = partnerId;
    }

    public Double getBookingAmt() {
        return bookingAmt;
    }

    public void setBookingAmt(Double bookingAmt) {
        this.bookingAmt = bookingAmt;
    }

    public Object getTeamId() {
        return teamId;
    }

    public void setTeamId(Object teamId) {
        this.teamId = teamId;
    }
}
