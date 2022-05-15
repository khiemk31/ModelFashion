package com.example.modelfashion.Model.response.my_product;

import com.example.modelfashion.Model.response.category.MyCategory;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataProduct {
    @SerializedName("data")
    private ArrayList<MyProductByCategory> data;

    public ArrayList<MyProductByCategory> getData() {
        return data;
    }

    public void setData(ArrayList<MyProductByCategory> data) {
        this.data = data;
    }

    public DataProduct(ArrayList<MyProductByCategory> data) {
        this.data = data;
    }

    public DataProduct() {
    }
}
