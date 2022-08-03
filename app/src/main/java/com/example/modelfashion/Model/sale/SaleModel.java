package com.example.modelfashion.Model.sale;

import java.util.ArrayList;
import java.util.List;

public class SaleModel {
    private String message;
    private ArrayList<ProductSale> listProduct;
    private int totalPage;

    public SaleModel(String message, ArrayList<ProductSale> listProduct, int totalPage) {
        this.message = message;
        this.listProduct = listProduct;
        this.totalPage = totalPage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ProductSale> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<ProductSale> listProduct) {
        this.listProduct = listProduct;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
