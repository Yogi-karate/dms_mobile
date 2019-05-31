
package com.dealermanagmentsystem.data.model.createenquiry;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnquireCreateRequest {

    @SerializedName("finance_type")
    @Expose
    private String financeType;
    @SerializedName("finance_amount")
    @Expose
    private Double financeAmount;
    @SerializedName("finance_agreement_date")
    @Expose
    private String financeAgreementDate;
    @SerializedName("insurance_type")
    @Expose
    private String insuranceType;
    @SerializedName("policy_punch_via")
    @Expose
    private String policyPunchVia;
    @SerializedName("type_ids")
    @Expose
    private List<Object> typeIds = null;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("date_follow_up")
    @Expose
    private String dateFollowUp;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;
    @SerializedName("partner_mobile")
    @Expose
    private String partnerMobile;
    @SerializedName("partner_email")
    @Expose
    private String partnerEmail;
    @SerializedName("source_id")
    @Expose
    private Integer sourceId;
    @SerializedName("product_variant")
    @Expose
    private Integer productVariant;
    @SerializedName("product_color")
    @Expose
    private Integer productColor;
    @SerializedName("insurance_company")
    @Expose
    private String insuranceCompany;
    @SerializedName("policy_no")
    @Expose
    private String policyNo;
    @SerializedName("idv")
    @Expose
    private Double idv;
    @SerializedName("insurance_valid_from")
    @Expose
    private String insuranceValidFrom;
    @SerializedName("insurance_valid_to")
    @Expose
    private String insuranceValidTo;
    @SerializedName("premium_amount")
    @Expose
    private Double premiumAmount;

    @SerializedName("test_drive")
    @Expose
    private Boolean testDrive;
    @SerializedName("test_drive_date")
    @Expose
    private String testDriveDate;

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

    public Double getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(Double financeAmount) {
        this.financeAmount = financeAmount;
    }

    public String getFinanceAgreementDate() {
        return financeAgreementDate;
    }

    public void setFinanceAgreementDate(String financeAgreementDate) {
        this.financeAgreementDate = financeAgreementDate;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getPolicyPunchVia() {
        return policyPunchVia;
    }

    public void setPolicyPunchVia(String policyPunchVia) {
        this.policyPunchVia = policyPunchVia;
    }

    public List<Object> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(List<Object> typeIds) {
        this.typeIds = typeIds;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getDateFollowUp() {
        return dateFollowUp;
    }

    public void setDateFollowUp(String dateFollowUp) {
        this.dateFollowUp = dateFollowUp;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerMobile() {
        return partnerMobile;
    }

    public void setPartnerMobile(String partnerMobile) {
        this.partnerMobile = partnerMobile;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(Integer productVariant) {
        this.productVariant = productVariant;
    }

    public Integer getProductColor() {
        return productColor;
    }

    public void setProductColor(Integer productColor) {
        this.productColor = productColor;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Double getIdv() {
        return idv;
    }

    public void setIdv(Double idv) {
        this.idv = idv;
    }

    public String getInsuranceValidFrom() {
        return insuranceValidFrom;
    }

    public void setInsuranceValidFrom(String insuranceValidFrom) {
        this.insuranceValidFrom = insuranceValidFrom;
    }

    public String getInsuranceValidTo() {
        return insuranceValidTo;
    }

    public void setInsuranceValidTo(String insuranceValidTo) {
        this.insuranceValidTo = insuranceValidTo;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Boolean getTestDrive() {
        return testDrive;
    }

    public void setTestDrive(Boolean testDrive) {
        this.testDrive = testDrive;
    }

    public String getTestDriveDate() {
        return testDriveDate;
    }

    public void setTestDriveDate(String testDriveDate) {
        this.testDriveDate = testDriveDate;
    }
}
