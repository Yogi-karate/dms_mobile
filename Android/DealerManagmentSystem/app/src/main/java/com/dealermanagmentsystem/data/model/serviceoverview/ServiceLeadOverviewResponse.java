
package com.dealermanagmentsystem.data.model.serviceoverview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceLeadOverviewResponse {

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("result")
    @Expose
    private Integer result = null;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
