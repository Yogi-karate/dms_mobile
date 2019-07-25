
package com.dealermanagmentsystem.data.model.userdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailResponse {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
