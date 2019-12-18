
package com.dealermanagmentsystem.data.model.socustomer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("product_variant")
    @Expose
    private String productVariant;
    @SerializedName("product_color")
    @Expose
    private String productColor;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date_order")
    @Expose
    private String dateOrder;
    @SerializedName("partner_id")
    @Expose
    private Object partnerId = null;
    @SerializedName("warehouse_id")
    @Expose
    private Object warehouseId = null;
    @SerializedName("product_name")
    @Expose
    private String productName;

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(String productVariant) {
        this.productVariant = productVariant;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Object getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Object partnerId) {
        this.partnerId = partnerId;
    }

    public Object getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Object warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
