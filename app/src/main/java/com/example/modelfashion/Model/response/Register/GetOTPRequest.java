package com.example.modelfashion.Model.response.Register;

import com.google.gson.annotations.SerializedName;

public class GetOTPRequest {
    @SerializedName("phone")
    private String phone;

    public GetOTPRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
