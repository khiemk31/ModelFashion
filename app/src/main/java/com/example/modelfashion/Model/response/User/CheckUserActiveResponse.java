package com.example.modelfashion.Model.response.User;

import com.google.gson.annotations.SerializedName;

public class CheckUserActiveResponse {
    @SerializedName("active")
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
