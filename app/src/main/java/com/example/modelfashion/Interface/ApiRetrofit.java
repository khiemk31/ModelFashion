package com.example.modelfashion.Interface;

import com.example.modelfashion.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRetrofit {
    Gson gson = new GsonBuilder().setLenient().create();
    ApiRetrofit apiRetrofit = new Retrofit.Builder().baseUrl("https://cuongb2k53lvt.000webhostapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiRetrofit.class);
    @Multipart
    @POST("FashionShop/upload_avatar.php")
    Call<String> uploadAvatar(@Part MultipartBody.Part avatar);
    @FormUrlEncoded
    @POST("FashionShop/insert_user.php")
    Call<String> InsertUser (@Field("taikhoan") String taikhoan, @Field("matkhau") String matkhau, @Field("avatar") String avatar);
    @FormUrlEncoded
    @POST("FashionShop/get_user.php")
    Call<ArrayList<User>> GetUser (@Field("taikhoan") String taikhoan, @Field("matkhau") String matkhau);
}
