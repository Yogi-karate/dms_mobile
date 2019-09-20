
package com.dealermanagmentsystem.data.model.bookinginsurance;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("previous_insurance_company")
    @Expose
    private Object previousInsuranceCompany = null;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cur_dip_or_comp")
    @Expose
    private String curDipOrComp;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("cur_final_premium")
    @Expose
    private String curFinalPremium;
    @SerializedName("rollover_company")
    @Expose
    private Object rolloverCompany = null;
    @SerializedName("idv")
    @Expose
    private String idv;
    @SerializedName("cur_ncb")
    @Expose
    private String curNcb;
    @SerializedName("policy_no")
    @Expose
    private String policyNo;
    @SerializedName("booking_type")
    @Expose
    private String bookingType;
    @SerializedName("pick_up_address")
    @Expose
    private String pickUpAddress;

    public Object getPreviousInsuranceCompany() {
        return previousInsuranceCompany;
    }

    public void setPreviousInsuranceCompany(Object previousInsuranceCompany) {
        this.previousInsuranceCompany = previousInsuranceCompany;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurDipOrComp() {
        return curDipOrComp;
    }

    public void setCurDipOrComp(String curDipOrComp) {
        this.curDipOrComp = curDipOrComp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCurFinalPremium() {
        return curFinalPremium;
    }

    public void setCurFinalPremium(String curFinalPremium) {
        this.curFinalPremium = curFinalPremium;
    }

    public Object getRolloverCompany() {
        return rolloverCompany;
    }

    public void setRolloverCompany(List<Integer> rolloverCompany) {
        this.rolloverCompany = rolloverCompany;
    }

    public String getIdv() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }

    public String getCurNcb() {
        return curNcb;
    }

    public void setCurNcb(String curNcb) {
        this.curNcb = curNcb;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

}
