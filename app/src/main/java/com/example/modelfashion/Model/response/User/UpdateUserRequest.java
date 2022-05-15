package com.example.modelfashion.Model.response.User;

import com.google.gson.annotations.SerializedName;

public class UpdateUserRequest {
    @SerializedName("user_name")
    private String userName;

    @SerializedName("gender")
    private int gender;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("address")
    private String address;

    public UpdateUserRequest(String userName, int gender, String dateOfBirth, String avatar, String address) {
        this.userName = userName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
