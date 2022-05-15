package com.example.modelfashion.Model.response.Register;

import com.google.gson.annotations.SerializedName;

public class GetOTPResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("otp_token")
    private String otpToken;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtpToken() {
        return otpToken;
    }

    public void setOtpToken(String otpToken) {
        this.otpToken = otpToken;
    }
}
