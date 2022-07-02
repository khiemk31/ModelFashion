package com.example.modelfashion.Model.response.main_screen;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("listProduct")
    private List<ProductMain> listProduct;
    @SerializedName("totalPage")
    private int totalPage;

    public GetAllResponse() {
    }

    public GetAllResponse(String message, List<ProductMain> listProduct, int totalPage) {
        this.message = message;
        this.listProduct = listProduct;
        this.totalPage = totalPage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductMain> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductMain> listProduct) {
        this.listProduct = listProduct;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "GetAllResponse{" +
                "message='" + message + '\'' +
                ", listProduct=" + listProduct +
                ", totalPage=" + totalPage +
                '}';
    }
}


