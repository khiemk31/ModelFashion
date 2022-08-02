package com.example.modelfashion.Model;

public class Voucher {
    private String mIDVoucher;
    private String mTitleVoucher;
    private String mHSD;
    private int mPriceVoucher;

    public Voucher(String mIDVoucher, String mTitleVoucher, String mHSD, int mPriceVoucher) {
        this.mIDVoucher = mIDVoucher;
        this.mTitleVoucher = mTitleVoucher;
        this.mHSD = mHSD;
        this.mPriceVoucher = mPriceVoucher;
    }

    public String getmIDVoucher() {
        return mIDVoucher;
    }

    public void setmIDVoucher(String mIDVoucher) {
        this.mIDVoucher = mIDVoucher;
    }

    public String getmTitleVoucher() {
        return mTitleVoucher;
    }

    public void setmTitleVoucher(String mTitleVoucher) {
        this.mTitleVoucher = mTitleVoucher;
    }

    public String getmHSD() {
        return mHSD;
    }

    public void setmHSD(String mHSD) {
        this.mHSD = mHSD;
    }

    public int getmPriceVoucher() {
        return mPriceVoucher;
    }

    public void setmPriceVoucher(int mPriceVoucher) {
        this.mPriceVoucher = mPriceVoucher;
    }
}
