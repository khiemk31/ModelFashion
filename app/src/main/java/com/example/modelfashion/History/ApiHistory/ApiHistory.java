package com.example.modelfashion.History.ApiHistory;

import com.example.modelfashion.Model.response.bill.Bill;

import com.example.modelfashion.Model.response.bill.BillDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHistory {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiHistory  API_HISTORY = new Retrofit.Builder()
            .baseUrl("https://model-fashion.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiHistory.class);
    @GET("bill/getListBill/{id}")
    Call<ArrayList<Bill>> getBill(@Path("id") String id);

    @GET("bill/getBillDetail/{id}")
    Call<BillDetail> getBillDetail(@Path("id") String id);


}
