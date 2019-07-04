
package com.dealermanagmentsystem.data.model.delivery;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryResponse {

    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("records")
    @Expose
    private List<Record> records = null;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
