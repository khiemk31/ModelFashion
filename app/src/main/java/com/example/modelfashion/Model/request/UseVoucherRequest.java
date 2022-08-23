package com.example.modelfashion.Model.request;

import com.google.gson.annotations.SerializedName;

public class UseVoucherRequest {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UseVoucherRequest(String id) {
        this.id = id;
    }

    public UseVoucherRequest() {
    }
}
