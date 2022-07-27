package com.example.modelfashion.Model.response.bill;

public class ContentBill {
    private String bill_id;
    private String status;
    private String phone;
    private String user_name;
    private String address;
    private String created_at;
    private int total_price;
    private int discount_voucher_price;
    private String cancellation_reason;
    private String feedback;
    private String return_request;
    private String feedback_by_store;


    public ContentBill(String bill_id, String status, String phone, String user_name, String address, String created_at, int total_price, int discount_voucher_price, String cancellation_reason, String feedback, String return_request, String feedback_by_store) {
        this.bill_id = bill_id;
        this.status = status;
        this.phone = phone;
        this.user_name = user_name;
        this.address = address;
        this.created_at = created_at;
        this.total_price = total_price;
        this.discount_voucher_price = discount_voucher_price;
        this.cancellation_reason = cancellation_reason;
        this.feedback = feedback;
        this.return_request = return_request;
        this.feedback_by_store = feedback_by_store;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getDiscount_voucher_price() {
        return discount_voucher_price;
    }

    public void setDiscount_voucher_price(int discount_voucher_price) {
        this.discount_voucher_price = discount_voucher_price;
    }

    public String getCancellation_reason() {
        return cancellation_reason;
    }

    public void setCancellation_reason(String cancellation_reason) {
        this.cancellation_reason = cancellation_reason;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getReturn_request() {
        return return_request;
    }

    public void setReturn_request(String return_request) {
        this.return_request = return_request;
    }

    public String getFeedback_by_store() {
        return feedback_by_store;
    }

    public void setFeedback_by_store(String feedback_by_store) {
        this.feedback_by_store = feedback_by_store;
    }
}
