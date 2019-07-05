
package com.dealermanagmentsystem.data.model.teamdetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamDetailResponse {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
