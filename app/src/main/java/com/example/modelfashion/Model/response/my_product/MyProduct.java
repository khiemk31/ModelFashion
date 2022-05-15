package com.example.modelfashion.Model.response.my_product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyProduct {
    private String product_id;
    private String product_name;
    private String product_image;
    private int price;
    private String category_name;
    private String size;
    private int quantity;

    private HashMap<String, Integer> sizeQuantity;
    private HashMap<String, Boolean> removeSameId;

    public HashMap<String, Boolean> getRemoveSameId() {
        return removeSameId;
    }

    public void setRemoveSameId(HashMap<String, Boolean> removeSameId) {
        this.removeSameId = removeSameId;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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

    public HashMap<String, Integer> getSizeQuantity() {
        return sizeQuantity;
    }

    public void setSizeQuantity(HashMap<String, Integer> sizeQuantity) {
        this.sizeQuantity = sizeQuantity;
    }

    public MyProduct(String product_id, String product_name, String product_image, int price, String category_name, String size, int quantity, HashMap<String, Integer> sizeQuantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.price = price;
        this.category_name = category_name;
        this.size = size;
        this.quantity = quantity;
        this.sizeQuantity = sizeQuantity;
    }

    public MyProduct() {
    }

    @Override
    public String toString() {
        return "MyProduct{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_image='" + product_image + '\'' +
                ", price=" + price +
                ", category_name='" + category_name + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", sizeQuantity=" + sizeQuantity +
                '}';
    }
}

