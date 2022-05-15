package com.example.modelfashion.Model.response.Register;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
