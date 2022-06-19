package com.example.modelfashion.Model.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseResponse() {
    }

    public BaseResponse(String message) {
        this.message = message;
    }
}
