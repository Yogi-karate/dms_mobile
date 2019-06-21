
package com.dealermanagmentsystem.data.model.delivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("scheduled_date")
    @Expose
    private String scheduledDate;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("picking_type_code")
    @Expose
    private String pickingTypeCode;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickingTypeCode() {
        return pickingTypeCode;
    }

    public void setPickingTypeCode(String pickingTypeCode) {
        this.pickingTypeCode = pickingTypeCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
