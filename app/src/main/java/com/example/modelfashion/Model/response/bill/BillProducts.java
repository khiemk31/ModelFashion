package com.example.modelfashion.Model.response.bill;

public class BillProducts {
    private String product_name;
    private String size;
    private String quantity;
    private String price;
    private String price_sale;
    private String product_image;

    public BillProducts(String product_name, String size, String quantity, String price, String price_sale, String product_image) {
        this.product_name = product_name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.price_sale = price_sale;
        this.product_image = product_image;
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

    public String getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(String price_sale) {
        this.price_sale = price_sale;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
