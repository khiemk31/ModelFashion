package com.example.modelfashion.Model.response.bill;

public class UpdateAdress {
    private String user_id;
    private String address;

    public UpdateAdress(String user_id, String address) {
        this.user_id = user_id;
        this.address = address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
