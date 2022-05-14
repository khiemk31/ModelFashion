package com.example.modelfashion.Model.response.Register;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPRequest {
    @SerializedName("otp_token")
    private String otpToken;
    @SerializedName("otp")
    private String otp;

    public VerifyOTPRequest(String otpToken, String otp) {
        this.otpToken = otpToken;
        this.otp = otp;
    }

    public String getOtpToken() {
        return otpToken;
    }

    public void setOtpToken(String otpToken) {
        this.otpToken = otpToken;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
