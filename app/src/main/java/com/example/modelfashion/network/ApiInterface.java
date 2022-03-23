package com.example.modelfashion.network;

import com.example.modelfashion.Model.response.User.User;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.product.ProductResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Single<CategoryResponse> getCategory(@Url String url);

    @GET("/api/products/categorys/{id}")
    Single<ProductResponse> getProductByCategory(@Path("id") String id);

    @GET("get_all_products.php")
    Single<ArrayList<MyProduct>> getAllProduct();

    @FormUrlEncoded
    @POST("insert_user.php")
    Call<String> insertUser (@Field("taikhoan") String taikhoan,
                             @Field("matkhau") String matkhau);

    @FormUrlEncoded
    @POST("check_login_user.php")
    Call<User> checkLogin (@Field("taikhoan") String taikhoan,
                           @Field("matkhau") String matkhau);
}
