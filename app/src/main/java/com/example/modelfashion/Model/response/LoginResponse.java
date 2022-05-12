package com.example.modelfashion.Model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private String data;
}
