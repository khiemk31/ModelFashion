package com.example.modelfashion.Model.response.Register;

import com.google.gson.annotations.SerializedName;

public class CheckUserRequest {
    @SerializedName("phone")
    private String phone;

    public CheckUserRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
