package com.example.modelfashion.Model.response.bill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Bill {
    private String bill_id;
    private String status;
    private String total_price;
    private String total_product;
    private String product_name;
    private String size;
    private String quantity;
    private String price;
    private String product_image;
    private String created_at;

    public Bill(String bill_id, String status, String total_price, String total_product, String product_name, String size, String quantity, String price, String product_image, String created_at) {
        this.bill_id = bill_id;
        this.status = status;
        this.total_price = total_price;
        this.total_product = total_product;
        this.product_name = product_name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.product_image = product_image;
        this.created_at = created_at;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_product() {
        return total_product;
    }

    public void setTotal_product(String total_product) {
        this.total_product = total_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
