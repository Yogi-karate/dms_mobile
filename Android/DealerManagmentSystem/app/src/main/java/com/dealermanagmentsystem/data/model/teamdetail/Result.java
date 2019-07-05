
package com.dealermanagmentsystem.data.model.teamdetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("user_id_count")
    @Expose
    private Integer userIdCount;
    @SerializedName("user_id")
    @Expose
    private Object userId = null;
    @SerializedName("user_booked_id")
    @Expose
    private Integer userBookedId;
    public Integer getUserIdCount() {
        return userIdCount;
    }

    public void setUserIdCount(Integer userIdCount) {
        this.userIdCount = userIdCount;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Integer getUserBookedId() {
        return userBookedId;
    }

    public void setUserBookedId(Integer userBookedId) {
        this.userBookedId = userBookedId;
    }
}
