package com.example.modelfashion.Interface;

import com.example.modelfashion.Model.User;
import com.example.modelfashion.Model.response.my_product.CartProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.Sizes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
    Call<String> InsertUser (@Field("taikhoan") String taikhoan, @Field("matkhau") String matkhau);

    @FormUrlEncoded
    @POST("FashionShop/get_products_size.php")
    Call<ArrayList<Sizes>> GetProductsSize(@Field("product_name") String product_name);

    @FormUrlEncoded
    @POST("FashionShop/check_quantity_left.php")
    Call<String> CheckSizeLeft(@Field("size_id") String size_id, @Field("quantity") String quantity);

    @FormUrlEncoded
    @POST("FashionShop/insert_bill_buy_now.php")
    Call<String> InsertBillBuyNow(@Field("user_id") String user_id, @Field("date") String date,
                                  @Field("price") String price, @Field("size_id") String size_id);

    @FormUrlEncoded
    @POST("FashionShop/insert_cart.php")
    Call<String> InsertCart(@Field("user_id") String user_id, @Field("size_id") String size_id,
                                @Field("product_name") String product_name, @Field("quantity") String quantity);

    @GET("FashionShop/get_user_cart.php")
    Call<ArrayList<CartProduct>> GetCartProduct(@Query("user_id") String user_id);

    @GET("FashionShop/get_product_by_name.php")
    Call<ArrayList<MyProduct>> GetProductByName(@Query("product_name") JSONArray product_name);

    @GET("FashionShop/get_sizes_by_id.php")
    Call<ArrayList<Sizes>> GetSizeById(@Query("sizes_id") JSONArray sizes_id);

    @GET("FashionShop/get_amount_cart.php")
    Call<String> GetAmountCart(@Query("product_name") JSONArray product_name);

    @FormUrlEncoded
    @POST("FashionShop/insert_payment.php")
    Call<String> InsertPayment(@Field("user_id") String user_id, @Field("amount") String amount,
                               @Field("date_created") String date_created, @Field("arr_sizes") String arr_sizes);
}
