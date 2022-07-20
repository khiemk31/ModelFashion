package com.example.modelfashion.Model;

import java.util.ArrayList;

public class CategoryModel {
    private String message;
    private ArrayList<Category> data = new ArrayList<>();

    public CategoryModel(String message, ArrayList<Category> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Category> getData() {
        return data;
    }

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }
}
