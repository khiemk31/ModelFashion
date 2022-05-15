package com.example.modelfashion.Model.response.my_product;

import com.google.gson.annotations.SerializedName;

public class ProductByCategory {
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("price")
    private int price;
    @SerializedName("size")
    private String size;
    @SerializedName("quantity")
    private int quantity;

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

    public ProductByCategory(String productId, String productName, String productImage, int price, String size, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    public ProductByCategory() {
    }
}
