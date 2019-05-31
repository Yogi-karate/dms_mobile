
package com.dealermanagmentsystem.data.model.enquiry;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;

    @SerializedName("user_id")
    @Expose
    private Object userId = null;
    @SerializedName("team_id")
    @Expose
    private Object teamId = null;

    @SerializedName("partner_mobile")
    @Expose
    private String partnerMobile;

    @SerializedName("date_follow_up")
    @Expose
    private String dateFollowUp;

    @SerializedName("date_deadline")
    @Expose
    private String dateDeadLine;

    @SerializedName("enquiry_id")
    @Expose
    private Object enquiryId = null;

    public String getPartnerMobile() {
        return partnerMobile;
    }

    public void setPartnerMobile(String partnerMobile) {
        this.partnerMobile = partnerMobile;
    }

    public String getDateFollowUp() {
        return dateFollowUp;
    }

    public void setDateFollowUp(String dateFollowUp) {
        this.dateFollowUp = dateFollowUp;
    }

    public String getDateDeadLine() {
        return dateDeadLine;
    }

    public void setDateDeadLine(String dateDeadLine) {
        this.dateDeadLine = dateDeadLine;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @SerializedName("mobile")
    @Expose
    private String mobile;

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getTeamId() {
        return teamId;
    }

    public void setTeamId(Object teamId) {
        this.teamId = teamId;
    }
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

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }


    public Object getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Object enquiryId) {
        this.enquiryId = enquiryId;
    }
}
