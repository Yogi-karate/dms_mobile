
package com.dealermanagmentsystem.data.model.leadoverview;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadOverviewResponse {

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
