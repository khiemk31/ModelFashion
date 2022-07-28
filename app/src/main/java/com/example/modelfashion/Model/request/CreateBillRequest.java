package com.example.modelfashion.Model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateBillRequest {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("product_list")
    private List<String> productList;
    @SerializedName("list_quantity")
    private List<String> listQuantity;
    @SerializedName("list_size")
    private List<String> listSize;
    @SerializedName("total_price")
    private String totalPrice;
    @SerializedName("price")
    private String price;
    @SerializedName("list_price")
    private List<String> listPrice;
    @SerializedName("list_price_sale")
    private List<String> listPriceSale;
    @SerializedName("discount_voucher_price")
    private String discountVoucherPrice;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("address")
    private String address;

    public CreateBillRequest(String userId, List<String> productList, List<String> listQuantity, List<String> listSize, String totalPrice, String price, List<String> listPrice, List<String> listPriceSale, String discountVoucherPrice, String productImage, String address) {
        this.userId = userId;
        this.productList = productList;
        this.listQuantity = listQuantity;
        this.listSize = listSize;
        this.totalPrice = totalPrice;
        this.price = price;
        this.listPrice = listPrice;
        this.listPriceSale = listPriceSale;
        this.discountVoucherPrice = discountVoucherPrice;
        this.productImage = productImage;
        this.address = address;
    }


    public List<String> getListPrice() {
        return listPrice;
    }

    public void setListPrice(List<String> listPrice) {
        this.listPrice = listPrice;
    }

    public List<String> getListPriceSale() {
        return listPriceSale;
    }

    public void setListPriceSale(List<String> listPriceSale) {
        this.listPriceSale = listPriceSale;
    }

    public String getDiscountVoucherPrice() {
        return discountVoucherPrice;
    }

    public void setDiscountVoucherPrice(String discountVoucherPrice) {
        this.discountVoucherPrice = discountVoucherPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CreateBillRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }

    public List<String> getListQuantity() {
        return listQuantity;
    }

    public void setListQuantity(List<String> listQuantity) {
        this.listQuantity = listQuantity;
    }

    public List<String> getListSize() {
        return listSize;
    }

    public void setListSize(List<String> listSize) {
        this.listSize = listSize;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public CreateBillRequest(String userId, List<String> productList, List<String> listQuantity, List<String> listSize, String totalPrice, String price, List<String> listPrice, List<String> listPriceSale, String discountVoucherPrice, String productImage) {
        this.userId = userId;
        this.productList = productList;
        this.listQuantity = listQuantity;
        this.listSize = listSize;
        this.totalPrice = totalPrice;
        this.price = price;
        this.listPrice = listPrice;
        this.listPriceSale = listPriceSale;
        this.discountVoucherPrice = discountVoucherPrice;
        this.productImage = productImage;
    }
}
