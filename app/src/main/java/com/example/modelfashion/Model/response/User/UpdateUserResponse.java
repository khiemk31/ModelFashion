package com.example.modelfashion.Model.response.User;

import com.google.gson.annotations.SerializedName;

public class UpdateUserResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
