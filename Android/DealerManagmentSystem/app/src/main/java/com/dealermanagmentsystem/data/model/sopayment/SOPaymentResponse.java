
package com.dealermanagmentsystem.data.model.sopayment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SOPaymentResponse {

    @SerializedName("records")
    @Expose
    private List<Record> records = null;
    @SerializedName("length")
    @Expose
    private Integer length;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
