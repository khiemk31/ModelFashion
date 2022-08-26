package com.example.modelfashion.Model.response.User;

import com.google.gson.annotations.SerializedName;

public class UserDetail {
    @SerializedName("user_id")
    private String userId;
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
    @SerializedName("number_of_orders")
    private int numberOfOrders;
    @SerializedName("money_spent")
    private int moneySpent;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(int moneySpent) {
        this.moneySpent = moneySpent;
    }

    public UserDetail(String userId, String phone, String username, String dateOfBirth, String address, int gender, String avatar, int numberOfOrders, int moneySpent) {
        this.userId = userId;
        this.phone = phone;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.avatar = avatar;
        this.numberOfOrders = numberOfOrders;
        this.moneySpent = moneySpent;
    }

    public UserDetail() {
    }
}
