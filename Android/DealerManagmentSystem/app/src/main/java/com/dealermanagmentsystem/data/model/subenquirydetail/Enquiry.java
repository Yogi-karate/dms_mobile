
package com.dealermanagmentsystem.data.model.subenquirydetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Enquiry {

    @SerializedName("product_variant")
    @Expose
    private Object productVariant = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_color")
    @Expose
    private Object productColor = null;
    @SerializedName("product_id")
    @Expose
    private Object productId = null;
    @SerializedName("source_id")
    @Expose
    private Object sourceId = null;
    @SerializedName("partner_email")
    @Expose
    private String partnerEmail = null;


    public Object getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(List<Integer> productVariant) {
        this.productVariant = productVariant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getProductColor() {
        return productColor;
    }

    public void setProductColor(Object productColor) {
        this.productColor = productColor;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public Object getSourceId() {
        return sourceId;
    }

    public void setSourceId(List<Integer> sourceId) {
        this.sourceId = sourceId;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }
}
