package com.example.modelfashion.Model.response.bill;

public class CancelBill {
    public String bill_id;
    public String cancellation_reason;

    public CancelBill(String bill_id, String cancellation_reason) {
        this.bill_id = bill_id;
        this.cancellation_reason = cancellation_reason;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getCancellation_reason() {
        return cancellation_reason;
    }

    public void setCancellation_reason(String cancellation_reason) {
        this.cancellation_reason = cancellation_reason;
    }
}
