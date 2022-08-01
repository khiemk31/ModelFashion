package com.example.modelfashion.Model.response.see_all;

import com.example.modelfashion.Model.response.main_screen.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductByCategoryResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("listProduct")
    private List<Product> listProduct;
    @SerializedName("totalPage")
    private int totalPage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public GetProductByCategoryResponse(String message, List<Product> listProduct, int totalPage) {
        this.message = message;
        this.listProduct = listProduct;
        this.totalPage = totalPage;
    }

    public GetProductByCategoryResponse() {
    }

    @Override
    public String toString() {
        return "GetProductByCategoryResponse{" +
                "message='" + message + '\'' +
                ", listProduct=" + listProduct +
                ", totalPage=" + totalPage +
                '}';
    }
}
