package com.example.modelfashion.Model.request;

import com.google.gson.annotations.SerializedName;

public class GetProductByPriceRequest {
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("price1")
    private long price1;
    @SerializedName("price2")
    private long price2;
    @SerializedName("sort")
    private String sort;

    public GetProductByPriceRequest(String categoryName, long price1, long price2, String sort) {
        this.categoryName = categoryName;
        this.price1 = price1;
        this.price2 = price2;
        this.sort = sort;
    }

    public GetProductByPriceRequest() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getPrice1() {
        return price1;
    }

    public void setPrice1(long price1) {
        this.price1 = price1;
    }

    public long getPrice2() {
        return price2;
    }

    public void setPrice2(long price2) {
        this.price2 = price2;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
