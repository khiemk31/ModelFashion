package com.example.modelfashion.Model.response.bill;

public class ContentBill {
    private String bill_id;
    private String phone;
    private String user_name;
    private String address;
    private String created_at;
    private String total_price;

    public ContentBill(String bill_id, String phone, String user_name, String address, String created_at, String total_price) {
        this.bill_id = bill_id;
        this.phone = phone;
        this.user_name = user_name;
        this.address = address;
        this.created_at = created_at;
        this.total_price = total_price;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
