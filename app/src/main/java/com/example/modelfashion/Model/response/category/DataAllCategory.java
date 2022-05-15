package com.example.modelfashion.Model.response.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataAllCategory {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<MyCategory> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MyCategory> getData() {
        return data;
    }

    public void setData(List<MyCategory> data) {
        this.data = data;
    }

    public DataAllCategory(String message, List<MyCategory> data) {
        this.message = message;
        this.data = data;
    }

    public DataAllCategory() {
    }
}
