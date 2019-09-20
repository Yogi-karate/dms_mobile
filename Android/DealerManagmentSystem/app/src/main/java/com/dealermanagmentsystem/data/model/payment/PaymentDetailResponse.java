
package com.dealermanagmentsystem.data.model.payment;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDetailResponse implements Serializable {

    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("records")
    @Expose
    private List<Record> records = null;
    @SerializedName("totalAmount")
    @Expose
    private Integer totalAmount;

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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

}
