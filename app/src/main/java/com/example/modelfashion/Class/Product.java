package com.example.modelfashion.Class;

public class Product {
    private String productName;
    private String productPrice;
    private int productImg;
    private String productType;

    public Product() {
    }

    public Product(String productName, String productPrice, int productImg, String productType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
