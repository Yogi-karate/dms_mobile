
package com.dealermanagmentsystem.data.model.saleorder.saleoverview;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("__domain")
    @Expose
    private List<List<String>> domain = null;
    @SerializedName("amount_tax")
    @Expose
    private Float amountTax;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("amount_untaxed")
    @Expose
    private Float amountUntaxed;
    @SerializedName("amount_total")
    @Expose
    private Float amountTotal;
    @SerializedName("currency_rate")
    @Expose
    private Integer currencyRate;
    @SerializedName("state_count")
    @Expose
    private Integer stateCount;

    public List<List<String>> getDomain() {
        return domain;
    }

    public void setDomain(List<List<String>> domain) {
        this.domain = domain;
    }

    public Float getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Float amountTax) {
        this.amountTax = amountTax;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getAmountUntaxed() {
        return amountUntaxed;
    }

    public void setAmountUntaxed(Float amountUntaxed) {
        this.amountUntaxed = amountUntaxed;
    }

    public Float getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Float amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Integer getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Integer currencyRate) {
        this.currencyRate = currencyRate;
    }

    public Integer getStateCount() {
        return stateCount;
    }

    public void setStateCount(Integer stateCount) {
        this.stateCount = stateCount;
    }

}
