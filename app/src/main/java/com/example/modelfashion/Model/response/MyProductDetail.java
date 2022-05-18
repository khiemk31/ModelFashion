package com.example.modelfashion.Model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyProductDetail {
    @SerializedName("product")
    private List<Product> product;
    @SerializedName("listSize")
    private List<Size> listSize;
    @SerializedName("productListImage")
    private List<ImageProductDetail> listImage;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Size> getListSize() {
        return listSize;
    }

    public void setListSize(List<Size> listSize) {
        this.listSize = listSize;
    }

    public List<ImageProductDetail> getListImage() {
        return listImage;
    }

    public void setListImage(List<ImageProductDetail> listImage) {
        this.listImage = listImage;
    }

    public MyProductDetail(List<Product> product, List<Size> listSize, List<ImageProductDetail> listImage) {
        this.product = product;
        this.listSize = listSize;
        this.listImage = listImage;
    }

    public MyProductDetail() {
    }

    @Override
    public String toString() {
        return "MyProductDetail{" +
                "product=" + product +
                '}';
    }
}

