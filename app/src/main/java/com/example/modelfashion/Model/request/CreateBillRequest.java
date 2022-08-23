package com.example.modelfashion.Model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateBillRequest {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("product_list")
    private List<String> productList;

    @SerializedName("total_price")
    private String totalPrice;

    @SerializedName("list_quantity")
    private List<String> listQuantity;

    @SerializedName("list_size")
    private List<String> listSize;

    @SerializedName("list_price")
    private List<String> listPrice;

    @SerializedName("list_price_sale")
    private List<String> listPriceSale;

    @SerializedName("discount_voucher_price")
    private String discountVoucherPrice;

    @SerializedName("address")
    private String address;

    @SerializedName("payment_status")
    private String paymentStatus;

    public CreateBillRequest(String userId, List<String> productList, String totalPrice, List<String> listQuantity, List<String> listSize, List<String> listPrice, List<String> listPriceSale, String discountVoucherPrice, String address, String paymentStatus) {
        this.userId = userId;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.listQuantity = listQuantity;
        this.listSize = listSize;
        this.listPrice = listPrice;
        this.listPriceSale = listPriceSale;
        this.discountVoucherPrice = discountVoucherPrice;
        this.address = address;
        this.paymentStatus = paymentStatus;
    }

    public CreateBillRequest(String userId, List<String> productList, String totalPrice, List<String> listQuantity, List<String> listSize, List<String> listPrice, List<String> listPriceSale) {
        this.userId = userId;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.listQuantity = listQuantity;
        this.listSize = listSize;
        this.listPrice = listPrice;
        this.listPriceSale = listPriceSale;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
