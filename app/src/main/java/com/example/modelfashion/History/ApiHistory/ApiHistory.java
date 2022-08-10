package com.example.modelfashion.History.ApiHistory;

import com.example.modelfashion.Model.CategoryModel;
import com.example.modelfashion.Model.VoucherCall;
import com.example.modelfashion.Model.response.bill.Address;
import com.example.modelfashion.Model.response.bill.Bill;

import com.example.modelfashion.Model.response.bill.BillDetail;
import com.example.modelfashion.Model.response.bill.CancelBill;
import com.example.modelfashion.Model.response.bill.FeedbackBill;
import com.example.modelfashion.Model.response.bill.RefundOfOrder;
import com.example.modelfashion.Model.response.bill.UpdateAdress;
import com.example.modelfashion.Model.response.bill.UserID;
import com.example.modelfashion.Model.response.see_all.GetProductByCategoryResponse;
import com.example.modelfashion.Model.sale.SaleModel;
import com.example.modelfashion.Utility.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHistory {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiHistory  API_HISTORY = new Retrofit.Builder()
            .baseUrl(Constants.URL_SERVER+"/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiHistory.class);
    @GET("bill/getListBill/{id}")
    Call<ArrayList<Bill>> getBill(@Path("id") String id);

    @GET("bill/getBillDetail/{id}")
    Call<BillDetail> getBillDetail(@Path("id") String id);

    @PUT("bill/cancelOrder")
    Call<CancelBill> cancelBill(@Body CancelBill cancelBill);
    @PUT("bill/returnRequest")
    Call<RefundOfOrder> refundOfOrder(@Body RefundOfOrder refundOfOrder);
    @PUT("bill/feedback")
    Call<FeedbackBill> feedbackBill(@Body FeedbackBill feedbackBill);

    @GET("user/getAddress/{id}")
    Call<Address> getAddress(@Path("id") String id);

    @PUT("user/updateAddress")
    Call<UpdateAdress> updateAddress(@Body UpdateAdress updateAdress);

    @GET("product/getProductDiscount")
    Call<SaleModel> getListSale();
    @GET("category/getAll")
    Call<CategoryModel> getListCategory();
    @GET("product/getAllProductDiscount")
    Call<SaleModel> getProductSaleByCategory(
            @Query("price1") int price1,
            @Query("price2") int price2,
            @Query("sortPrice") String sortPrice,
            @Query("sortDiscount") String sortDiscount,
            @Query("pageNumber") int pageNumber
    );
    @GET("getVoucherUser")
    Call<VoucherCall> getListVoucher(@Query("id") String id);


}
