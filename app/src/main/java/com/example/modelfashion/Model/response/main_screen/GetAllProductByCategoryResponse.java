package com.example.modelfashion.Model.response.main_screen;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllProductByCategoryResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("listAllProduct")
    private List<List<Product>> productList;

    public GetAllProductByCategoryResponse(String message, List<List<Product>> productList) {
        this.message = message;
        this.productList = productList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<List<Product>> getProductList() {
        return productList;
    }

    public void setProductList(List<List<Product>> productList) {
        this.productList = productList;
    }

    public GetAllProductByCategoryResponse() {
    }
}
