package com.example.modelfashion.Model.sale;

import java.util.ArrayList;
import java.util.List;

public class SaleModel {
    private String message;
    private ArrayList<ProductSale> listProduct;

    public SaleModel(String message, ArrayList<ProductSale> listProduct) {
        this.message = message;
        this.listProduct = listProduct;
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
}
