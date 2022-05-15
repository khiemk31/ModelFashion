package com.example.modelfashion.Model.response.Register;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("phone")
    private String phone;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("gender")
    private int gender;

    public RegisterRequest(String phone, String userName, String password, int gender) {
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
