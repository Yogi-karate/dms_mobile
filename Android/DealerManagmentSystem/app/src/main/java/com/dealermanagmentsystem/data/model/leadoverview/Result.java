
package com.dealermanagmentsystem.data.model.leadoverview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

   /* @SerializedName("__domain")
    @Expose
    private Object domain = null;*/
  /*  @SerializedName("color")
    @Expose
    private Object color;
    @SerializedName("planned_revenue")
    @Expose
    private Boolean plannedRevenue;
    @SerializedName("day_open")
    @Expose
    private Object dayOpen;*/
  /*  @SerializedName("message_bounce")
    @Expose
    private Integer messageBounce;
    @SerializedName("__fold")
    @Expose
    private Boolean fold;
    @SerializedName("day_close")
    @Expose
    private Object dayClose;*/
    @SerializedName("stage_id")
    @Expose
    private Object stageId = null;
    @SerializedName("stage_id_count")
    @Expose
    private Integer stageIdCount;
/*
    @SerializedName("days_open")
    @Expose
    private Object daysOpen;
    @SerializedName("expected_revenue")
    @Expose
    private Object expectedRevenue;
    @SerializedName("probability")
    @Expose
    private Object probability;
*/

   /* public Object getDomain() {
        return domain;
    }

    public void setDomain(Object domain) {
        this.domain = domain;
    }
*/
   /* public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public Boolean getPlannedRevenue() {
        return plannedRevenue;
    }

    public void setPlannedRevenue(Boolean plannedRevenue) {
        this.plannedRevenue = plannedRevenue;
    }

    public Object getDayOpen() {
        return dayOpen;
    }

    public void setDayOpen(Object dayOpen) {
        this.dayOpen = dayOpen;
    }
*/
   /* public Integer getMessageBounce() {
        return messageBounce;
    }

    public void setMessageBounce(Integer messageBounce) {
        this.messageBounce = messageBounce;
    }

    public Boolean getFold() {
        return fold;
    }

    public void setFold(Boolean fold) {
        this.fold = fold;
    }

    public Object getDayClose() {
        return dayClose;
    }

    public void setDayClose(Object dayClose) {
        this.dayClose = dayClose;
    }
*/
    public Object getStageId() {
        return stageId;
    }

    public void setStageId(Object stageId) {
        this.stageId = stageId;
    }

    public Integer getStageIdCount() {
        return stageIdCount;
    }

    public void setStageIdCount(Integer stageIdCount) {
        this.stageIdCount = stageIdCount;
    }

  /*  public Object getDaysOpen() {
        return daysOpen;
    }

    public void setDaysOpen(Object daysOpen) {
        this.daysOpen = daysOpen;
    }

    public Object getExpectedRevenue() {
        return expectedRevenue;
    }

    public void setExpectedRevenue(Object expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
    }

    public Object getProbability() {
        return probability;
    }

    public void setProbability(Object probability) {
        this.probability = probability;
    }
*/
}
