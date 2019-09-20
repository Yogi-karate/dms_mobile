
package com.dealermanagmentsystem.data.model.loadusers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadUserResponse {

    @SerializedName("users")
    @Expose
    private List<User> users = null;

    @SerializedName("new_user_count")
    @Expose
    private Integer newUserCount;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getNewUserCount() {
        return newUserCount;
    }

    public void setNewUserCount(Integer newUserCount) {
        this.newUserCount = newUserCount;
    }
}
