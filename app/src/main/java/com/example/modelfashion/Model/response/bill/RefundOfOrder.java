package com.example.modelfashion.Model.response.bill;

public class RefundOfOrder {
    private String bill_id;
    private String return_request;

    public RefundOfOrder(String bill_id, String return_request) {
        this.bill_id = bill_id;
        this.return_request = return_request;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getReturn_request() {
        return return_request;
    }

    public void setReturn_request(String return_request) {
        this.return_request = return_request;
    }
}
