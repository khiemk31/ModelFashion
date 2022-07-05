package com.example.modelfashion.Model.response;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private int price;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("product_bgr1")
    private String productBgr1;
    @SerializedName("product_bgr2")
    private String productBgr2;
    @SerializedName("product_bgr3")
    private String productBgr3;

    public Product(String productId, String productName, int price, String productImage, String productBgr1, String productBgr2, String productBgr3) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.productBgr1 = productBgr1;
        this.productBgr2 = productBgr2;
        this.productBgr3 = productBgr3;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductBgr1() {
        return productBgr1;
    }

    public void setProductBgr1(String productBgr1) {
        this.productBgr1 = productBgr1;
    }

    public String getProductBgr2() {
        return productBgr2;
    }

    public void setProductBgr2(String productBgr2) {
        this.productBgr2 = productBgr2;
    }

    public String getProductBgr3() {
        return productBgr3;
    }

    public void setProductBgr3(String productBgr3) {
        this.productBgr3 = productBgr3;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                '}';
    }
}
