package com.example.modelfashion.Model.response.bill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BillDetail {
    private ArrayList<ContentBill> bill = new ArrayList<>();
    private ArrayList<BillProducts> listProduct = new ArrayList<>();

    public ArrayList<ContentBill> getBill() {
        return bill;
    }

    public void setBill(ArrayList<ContentBill> bill) {
        this.bill = bill;
    }

    public ArrayList<BillProducts> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<BillProducts> listProduct) {
        this.listProduct = listProduct;
    }
}
