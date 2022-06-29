package com.example.modelfashion.Model.sale;

public class ProductSale {
    private String product_id;
    private String product_name;
    private int price;
    private String product_image;
    private int discount;

    public ProductSale(String product_id, String product_name, int price, String product_image, int discount) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.product_image = product_image;
        this.discount = discount;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
