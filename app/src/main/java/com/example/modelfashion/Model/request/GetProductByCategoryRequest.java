package com.example.modelfashion.Model.request;

import com.google.gson.annotations.SerializedName;

public class GetProductByCategoryRequest {
    @SerializedName("category_id")
    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public GetProductByCategoryRequest() {
    }

    public GetProductByCategoryRequest(String categoryId) {
        this.categoryId = categoryId;
    }
}
