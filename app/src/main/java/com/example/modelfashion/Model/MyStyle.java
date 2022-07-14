package com.example.modelfashion.Model;

public class MyStyle {
    private String nameCategory;
    private String imgCategory;

    public MyStyle(String nameCategory, String imgCategory) {
        this.nameCategory = nameCategory;
        this.imgCategory = imgCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }
}
