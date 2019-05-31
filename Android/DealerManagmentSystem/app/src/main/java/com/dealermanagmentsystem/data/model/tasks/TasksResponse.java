
package com.dealermanagmentsystem.data.model.tasks;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TasksResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("activity_type_id")
    @Expose
    private Object activityTypeId = null;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("date_deadline")
    @Expose
    private String dateDeadline;
    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("user_id")
    @Expose
    private Object userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Object activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(String dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }
}
