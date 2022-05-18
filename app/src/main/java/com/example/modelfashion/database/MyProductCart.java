package com.example.modelfashion.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "product_cart")
public class MyProductCart {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "product_id")
    private String productId;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "product_price")
    private int productPrice;
    @ColumnInfo(name = "product_size")
    private String productSize;
    @ColumnInfo(name = "product_quantity")
    private int productQuantity;
    @ColumnInfo(name = "product_image")
    private String productImage;

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public MyProductCart(String productId, String productName, int productPrice, String productSize, int productQuantity, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
    }

    public MyProductCart() {
    }

    public Long tongTien() {
        return (long) this.productQuantity * this.productPrice;
    }

    @Override
    public String toString() {
        return "MyProductCart{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productSize='" + productSize + '\'' +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
