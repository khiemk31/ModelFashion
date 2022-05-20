package com.example.modelfashion.Model.response.User;

import com.google.gson.annotations.SerializedName;

public class UserDetail {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("role")
    private String role;
    @SerializedName("active")
    private int active;
    @SerializedName("phone")
    private String phone;
    @SerializedName("user_name")
    private String username;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @SerializedName("address")
    private String address;
    @SerializedName("gender")
    private int gender;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("password")
    private String password;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updated_at;
    @SerializedName("deletedAt")
    private String deleted_at;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public UserDetail(String userId, String role, int active, String phone, String username, String dateOfBirth, String address, int gender, String avatar, String password, String createdAt, String updated_at, String deleted_at) {
        this.userId = userId;
        this.role = role;
        this.active = active;
        this.phone = phone;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.avatar = avatar;
        this.password = password;
        this.createdAt = createdAt;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public UserDetail() {
    }

    public Boolean getRealActive(){
        if (this.active == 0)
            return true;
        else  return false;
    }
}
