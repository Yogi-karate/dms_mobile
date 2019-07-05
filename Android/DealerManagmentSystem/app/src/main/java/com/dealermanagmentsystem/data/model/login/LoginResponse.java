
package com.dealermanagmentsystem.data.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("auth")
    @Expose
    private Boolean auth;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("teams")
    @Expose
    private Teams teams;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("message")
    @Expose
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
