package com.example.modelfashion.Model.response.my_product;

import com.google.gson.annotations.SerializedName;

public class MyProductByCategory {
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("price")
    private int price;
    @SerializedName("category_name")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public MyProductByCategory(String productId, String productName, String productImage, int price, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.categoryName = categoryName;
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

    public MyProductByCategory(String productId, String productName, String productImage, int price) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
    }

    public MyProductByCategory() {
    }
}
