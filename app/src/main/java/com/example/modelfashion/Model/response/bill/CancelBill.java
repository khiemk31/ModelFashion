package com.example.modelfashion.Model.response.bill;

public class CancelBill {
    public String bill_id;

    public CancelBill(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
}
