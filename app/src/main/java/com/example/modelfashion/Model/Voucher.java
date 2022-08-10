package com.example.modelfashion.Model;

public class Voucher {
    private String id;
    private String title;
    private String code;
    private String user_id;
    private int price_discount;
    private int active;
    private String expired;

    public Voucher(String id, String title, String code, String user_id, int price_discount, int active, String expired) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.user_id = user_id;
        this.price_discount = price_discount;
        this.active = active;
        this.expired = expired;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getPrice_discount() {
        return price_discount;
    }

    public void setPrice_discount(int price_discount) {
        this.price_discount = price_discount;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
