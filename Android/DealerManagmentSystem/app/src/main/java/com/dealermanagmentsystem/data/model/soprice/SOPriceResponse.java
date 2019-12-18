
package com.dealermanagmentsystem.data.model.soprice;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SOPriceResponse {

    @SerializedName("records")
    @Expose
    private List<Record> records = null;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("saleorder")
    @Expose
    private SaleOrder saleorder;

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

    public SaleOrder getSaleorder() {
        return saleorder;
    }

    public void setSaleorder(SaleOrder saleorder) {
        this.saleorder = saleorder;
    }

}
