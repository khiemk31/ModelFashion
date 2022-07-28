package com.example.modelfashion.Model.response.User;

import com.google.gson.annotations.SerializedName;

public class CheckUserActiveRequest {
    @SerializedName("user_id")
    private String user_id;

    public CheckUserActiveRequest(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
