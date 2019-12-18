
package com.dealermanagmentsystem.data.model.subenquirydetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("user_id")
    @Expose
    private Object userId = null;
    @SerializedName("sale_number")
    @Expose
    private Integer saleNumber;
    @SerializedName("team_id")
    @Expose
    private Object teamId = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("stage_id")
    @Expose
    private Object stageId = null;
    @SerializedName("enquiry_id")
    @Expose
    private Object enquiryId = null;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("enquiry")
    @Expose
    private Enquiry enquiry;


    @SerializedName("activity_date_deadline")
    @Expose
    private String activityDateDeadline;
    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Object getTeamId() {
        return teamId;
    }

    public void setTeamId(List<Integer> teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getStageId() {
        return stageId;
    }

    public void setStageId(Object stageId) {
        this.stageId = stageId;
    }

    public Object getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Object enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public String getActivityDateDeadline() {
        return activityDateDeadline;
    }

    public void setActivityDateDeadline(String activityDateDeadline) {
        this.activityDateDeadline = activityDateDeadline;
    }
}
