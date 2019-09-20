
package com.dealermanagmentsystem.data.model.appupdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppUpdateResponse {

    @SerializedName("versionCode")
    @Expose
    private Integer versionCode;
    @SerializedName("cancellable")
    @Expose
    private Boolean cancellable;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("versionName")
    @Expose
    private String versionName;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
