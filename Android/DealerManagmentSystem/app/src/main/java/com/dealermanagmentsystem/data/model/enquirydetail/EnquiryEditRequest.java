
package com.dealermanagmentsystem.data.model.enquirydetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnquiryEditRequest {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("partner_id")
    @Expose
    private String partnerId;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("team_id")
    @Expose
    private List<Object> teamId = null;
    @SerializedName("user_id")
    @Expose
    private List<Object> userId = null;
    @SerializedName("company_id")
    @Expose
    private List<Object> companyId = null;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("product_id")
    @Expose
    private Integer productId = null;
    @SerializedName("product_color")
    @Expose
    private String productColor;
    @SerializedName("product_variant")
    @Expose
    private String productVariant;
    @SerializedName("opportunity_ids")
    @Expose
    private List<Integer> opportunityIds = null;
    @SerializedName("type_ids")
    @Expose
    private List<Integer> typeIds = null;
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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("financier_name")
    @Expose
    private Object financierName;
    @SerializedName("finance_amount")
    @Expose
    private Double financeAmount;
    @SerializedName("date_order")
    @Expose
    private String dateOrder;
    @SerializedName("finance_agreement_date")
    @Expose
    private String financeAgreementDate;
    @SerializedName("loan_tenure")
    @Expose
    private String loanTenure;
    @SerializedName("loan_amount")
    @Expose
    private Integer loanAmount;
    @SerializedName("loan_approved_amount")
    @Expose
    private Integer loanApprovedAmount;
    @SerializedName("loan_rate")
    @Expose
    private Integer loanRate;
    @SerializedName("loan_emi")
    @Expose
    private Integer loanEmi;
    @SerializedName("loan_commission")
    @Expose
    private Integer loanCommission;
    @SerializedName("finance_type")
    @Expose
    private String financeType;
    @SerializedName("insurance_company")
    @Expose
    private String insuranceCompany;
    @SerializedName("policy_no")
    @Expose
    private String policyNo;
    @SerializedName("insurance_valid_from")
    @Expose
    private String insuranceValidFrom;
    @SerializedName("insurance_valid_to")
    @Expose
    private String insuranceValidTo;
    @SerializedName("insurance_type")
    @Expose
    private String insuranceType;
    @SerializedName("policy_punch_via")
    @Expose
    private String policyPunchVia;
    @SerializedName("currency_id")
    @Expose
    private String currencyId;
    @SerializedName("idv")
    @Expose
    private Double idv;
    @SerializedName("premium_amount")
    @Expose
    private Double premiumAmount;
    @SerializedName("source_id")
    @Expose
    private Integer sourceId = null;
    @SerializedName("medium_id")
    @Expose
    private String mediumId;
    @SerializedName("activity_ids")
    @Expose
    private List<Object> activityIds = null;
    @SerializedName("message_follower_ids")
    @Expose
    private List<Integer> messageFollowerIds = null;
    @SerializedName("message_ids")
    @Expose
    private List<Integer> messageIds = null;
    @SerializedName("message_main_attachment_id")
    @Expose
    private String messageMainAttachmentId;
    @SerializedName("website_message_ids")
    @Expose
    private List<Object> websiteMessageIds = null;
    @SerializedName("create_uid")
    @Expose
    private List<Object> createUid = null;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("write_uid")
    @Expose
    private List<Object> writeUid = null;
    @SerializedName("write_date")
    @Expose
    private String writeDate;
    @SerializedName("categ_ids")
    @Expose
    private List<Integer> categIds = null;
    @SerializedName("product_updatable")
    @Expose
    private String productUpdatable;
    @SerializedName("finance_updatable")
    @Expose
    private Boolean financeUpdatable;
    @SerializedName("insurance_updatable")
    @Expose
    private String insuranceUpdatable;
    @SerializedName("opportunity_count")
    @Expose
    private Integer opportunityCount;
    @SerializedName("variant_attribute_values")
    @Expose
    private List<Integer> variantAttributeValues = null;
    @SerializedName("color_attribute_values")
    @Expose
    private List<Object> colorAttributeValues = null;
    @SerializedName("activity_state")
    @Expose
    private String activityState;
    @SerializedName("activity_user_id")
    @Expose
    private String activityUserId;
    @SerializedName("activity_type_id")
    @Expose
    private String activityTypeId;
    @SerializedName("activity_date_deadline")
    @Expose
    private String activityDateDeadline;
    @SerializedName("activity_summary")
    @Expose
    private String activitySummary;
    @SerializedName("message_is_follower")
    @Expose
    private Boolean messageIsFollower;
    @SerializedName("message_partner_ids")
    @Expose
    private List<Integer> messagePartnerIds = null;
    @SerializedName("message_channel_ids")
    @Expose
    private List<Object> messageChannelIds = null;
    @SerializedName("message_unread")
    @Expose
    private String messageUnread;
    @SerializedName("message_unread_counter")
    @Expose
    private Integer messageUnreadCounter;
    @SerializedName("message_needaction")
    @Expose
    private String messageNeedaction;
    @SerializedName("message_needaction_counter")
    @Expose
    private Integer messageNeedactionCounter;
    @SerializedName("message_has_error")
    @Expose
    private String messageHasError;
    @SerializedName("message_has_error_counter")
    @Expose
    private Integer messageHasErrorCounter;
    @SerializedName("message_attachment_count")
    @Expose
    private Integer messageAttachmentCount;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("__last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("test_drive")
    @Expose
    private Boolean testDrive;
    @SerializedName("test_drive_date")
    @Expose
    private String testDriveDate;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Object> getTeamId() {
        return teamId;
    }

    public void setTeamId(List<Object> teamId) {
        this.teamId = teamId;
    }

    public List<Object> getUserId() {
        return userId;
    }

    public void setUserId(List<Object> userId) {
        this.userId = userId;
    }

    public List<Object> getCompanyId() {
        return companyId;
    }

    public void setCompanyId(List<Object> companyId) {
        this.companyId = companyId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(String productVariant) {
        this.productVariant = productVariant;
    }

    public List<Integer> getOpportunityIds() {
        return opportunityIds;
    }

    public void setOpportunityIds(List<Integer> opportunityIds) {
        this.opportunityIds = opportunityIds;
    }

    public List<Integer> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(List<Integer> typeIds) {
        this.typeIds = typeIds;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getFinancierName() {
        return financierName;
    }

    public void setFinancierName(Object financierName) {
        this.financierName = financierName;
    }

    public Double getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(Double financeAmount) {
        this.financeAmount = financeAmount;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getFinanceAgreementDate() {
        return financeAgreementDate;
    }

    public void setFinanceAgreementDate(String financeAgreementDate) {
        this.financeAgreementDate = financeAgreementDate;
    }

    public String getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(String loanTenure) {
        this.loanTenure = loanTenure;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanApprovedAmount() {
        return loanApprovedAmount;
    }

    public void setLoanApprovedAmount(Integer loanApprovedAmount) {
        this.loanApprovedAmount = loanApprovedAmount;
    }

    public Integer getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(Integer loanRate) {
        this.loanRate = loanRate;
    }

    public Integer getLoanEmi() {
        return loanEmi;
    }

    public void setLoanEmi(Integer loanEmi) {
        this.loanEmi = loanEmi;
    }

    public Integer getLoanCommission() {
        return loanCommission;
    }

    public void setLoanCommission(Integer loanCommission) {
        this.loanCommission = loanCommission;
    }

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
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

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Double getIdv() {
        return idv;
    }

    public void setIdv(Double idv) {
        this.idv = idv;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public List<Object> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<Object> activityIds) {
        this.activityIds = activityIds;
    }

    public List<Integer> getMessageFollowerIds() {
        return messageFollowerIds;
    }

    public void setMessageFollowerIds(List<Integer> messageFollowerIds) {
        this.messageFollowerIds = messageFollowerIds;
    }

    public List<Integer> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Integer> messageIds) {
        this.messageIds = messageIds;
    }

    public String getMessageMainAttachmentId() {
        return messageMainAttachmentId;
    }

    public void setMessageMainAttachmentId(String messageMainAttachmentId) {
        this.messageMainAttachmentId = messageMainAttachmentId;
    }

    public List<Object> getWebsiteMessageIds() {
        return websiteMessageIds;
    }

    public void setWebsiteMessageIds(List<Object> websiteMessageIds) {
        this.websiteMessageIds = websiteMessageIds;
    }

    public List<Object> getCreateUid() {
        return createUid;
    }

    public void setCreateUid(List<Object> createUid) {
        this.createUid = createUid;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<Object> getWriteUid() {
        return writeUid;
    }

    public void setWriteUid(List<Object> writeUid) {
        this.writeUid = writeUid;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public List<Integer> getCategIds() {
        return categIds;
    }

    public void setCategIds(List<Integer> categIds) {
        this.categIds = categIds;
    }

    public String getProductUpdatable() {
        return productUpdatable;
    }

    public void setProductUpdatable(String productUpdatable) {
        this.productUpdatable = productUpdatable;
    }

    public Boolean getFinanceUpdatable() {
        return financeUpdatable;
    }

    public void setFinanceUpdatable(Boolean financeUpdatable) {
        this.financeUpdatable = financeUpdatable;
    }

    public String getInsuranceUpdatable() {
        return insuranceUpdatable;
    }

    public void setInsuranceUpdatable(String insuranceUpdatable) {
        this.insuranceUpdatable = insuranceUpdatable;
    }

    public Integer getOpportunityCount() {
        return opportunityCount;
    }

    public void setOpportunityCount(Integer opportunityCount) {
        this.opportunityCount = opportunityCount;
    }

    public List<Integer> getVariantAttributeValues() {
        return variantAttributeValues;
    }

    public void setVariantAttributeValues(List<Integer> variantAttributeValues) {
        this.variantAttributeValues = variantAttributeValues;
    }

    public List<Object> getColorAttributeValues() {
        return colorAttributeValues;
    }

    public void setColorAttributeValues(List<Object> colorAttributeValues) {
        this.colorAttributeValues = colorAttributeValues;
    }

    public String getActivityState() {
        return activityState;
    }

    public void setActivityState(String activityState) {
        this.activityState = activityState;
    }

    public String getActivityUserId() {
        return activityUserId;
    }

    public void setActivityUserId(String activityUserId) {
        this.activityUserId = activityUserId;
    }

    public String getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityDateDeadline() {
        return activityDateDeadline;
    }

    public void setActivityDateDeadline(String activityDateDeadline) {
        this.activityDateDeadline = activityDateDeadline;
    }

    public String getActivitySummary() {
        return activitySummary;
    }

    public void setActivitySummary(String activitySummary) {
        this.activitySummary = activitySummary;
    }

    public Boolean getMessageIsFollower() {
        return messageIsFollower;
    }

    public void setMessageIsFollower(Boolean messageIsFollower) {
        this.messageIsFollower = messageIsFollower;
    }

    public List<Integer> getMessagePartnerIds() {
        return messagePartnerIds;
    }

    public void setMessagePartnerIds(List<Integer> messagePartnerIds) {
        this.messagePartnerIds = messagePartnerIds;
    }

    public List<Object> getMessageChannelIds() {
        return messageChannelIds;
    }

    public void setMessageChannelIds(List<Object> messageChannelIds) {
        this.messageChannelIds = messageChannelIds;
    }

    public String getMessageUnread() {
        return messageUnread;
    }

    public void setMessageUnread(String messageUnread) {
        this.messageUnread = messageUnread;
    }

    public Integer getMessageUnreadCounter() {
        return messageUnreadCounter;
    }

    public void setMessageUnreadCounter(Integer messageUnreadCounter) {
        this.messageUnreadCounter = messageUnreadCounter;
    }

    public String getMessageNeedaction() {
        return messageNeedaction;
    }

    public void setMessageNeedaction(String messageNeedaction) {
        this.messageNeedaction = messageNeedaction;
    }

    public Integer getMessageNeedactionCounter() {
        return messageNeedactionCounter;
    }

    public void setMessageNeedactionCounter(Integer messageNeedactionCounter) {
        this.messageNeedactionCounter = messageNeedactionCounter;
    }

    public String getMessageHasError() {
        return messageHasError;
    }

    public void setMessageHasError(String messageHasError) {
        this.messageHasError = messageHasError;
    }

    public Integer getMessageHasErrorCounter() {
        return messageHasErrorCounter;
    }

    public void setMessageHasErrorCounter(Integer messageHasErrorCounter) {
        this.messageHasErrorCounter = messageHasErrorCounter;
    }

    public Integer getMessageAttachmentCount() {
        return messageAttachmentCount;
    }

    public void setMessageAttachmentCount(Integer messageAttachmentCount) {
        this.messageAttachmentCount = messageAttachmentCount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
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
