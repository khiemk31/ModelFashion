package com.example.modelfashion.Model.response.bill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Bill {
    @SerializedName("bill_id")
    @Expose
    private String billId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_shipped")
    @Expose
    private String dateShipped;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("bill_detail")
    @Expose
    private ArrayList<BillDetail> billDetail = null;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(String dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ArrayList<BillDetail> getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(ArrayList<BillDetail> billDetail) {
        this.billDetail = billDetail;
    }
}
