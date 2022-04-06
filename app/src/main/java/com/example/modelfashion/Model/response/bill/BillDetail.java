package com.example.modelfashion.Model.response.bill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class BillDetail {
    @SerializedName("detail_id")
    @Expose
    private String detailId;
    @SerializedName("product_size_id")
    @Expose
    private String productSizeId;
    @SerializedName("bill_id")
    @Expose
    private String billId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("size")
    @Expose
    private String size;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
