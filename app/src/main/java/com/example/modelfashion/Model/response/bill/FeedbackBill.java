package com.example.modelfashion.Model.response.bill;

public class FeedbackBill {
    private String bill_id;
    private String feedback;

    public FeedbackBill(String bill_id, String feedback) {
        this.bill_id = bill_id;
        this.feedback = feedback;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
