package com.example.modelfashion.Model.response.User;


import com.google.gson.annotations.SerializedName;

public class UserDetailResponse {
    @SerializedName("data")
    private UserDetail data;

    public UserDetail getData() {
        return data;
    }

    public void setData(UserDetail data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserDetailResponse{" +
                "data=" + data +
                '}';
    }
}
