package com.example.modelfashion.Model.response.main_screen;

import com.google.gson.annotations.SerializedName;

public class ProductMain {
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("price")
    private int price;

    public ProductMain() {
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductMain(String productId, String productName, String productImage, int price) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductMain{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", price=" + price +
                '}';
    }
}