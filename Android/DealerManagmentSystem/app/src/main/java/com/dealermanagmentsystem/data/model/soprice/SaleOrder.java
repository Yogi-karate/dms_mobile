
package com.dealermanagmentsystem.data.model.soprice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleOrder {

    @SerializedName("amount_untaxed")
    @Expose
    private Double amountUntaxed;
    @SerializedName("amount_total")
    @Expose
    private Double amountTotal;
    @SerializedName("amount_tax")
    @Expose
    private Double amountTax;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Double getAmountUntaxed() {
        return amountUntaxed;
    }

    public void setAmountUntaxed(Double amountUntaxed) {
        this.amountUntaxed = amountUntaxed;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
