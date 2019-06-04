
package com.dealermanagmentsystem.data.model.saleorder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("amount_total")
    @Expose
    private String amountTotal;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("invoice_status")
    @Expose
    private String invoiceStatus;
    @SerializedName("date_order")
    @Expose
    private String dateOrder;

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getTeamId() {
        return teamId;
    }

    public void setTeamId(Object teamId) {
        this.teamId = teamId;
    }

    @SerializedName("user_id")
    @Expose
    private Object userId = null;
    @SerializedName("team_id")
    @Expose
    private Object teamId = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }
}
