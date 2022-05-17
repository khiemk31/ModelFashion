package com.example.modelfashion.Model.response;

import com.google.gson.annotations.SerializedName;

public class Size {
    @SerializedName("size")
    private String size;
    @SerializedName("quantity")
    private int quantity;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Size(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public Size() {
    }
}
