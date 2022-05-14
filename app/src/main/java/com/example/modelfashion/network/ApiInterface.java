package com.example.modelfashion.network;

import com.example.modelfashion.Model.response.Login.LoginRequest;
import com.example.modelfashion.Model.response.Login.LoginResponse;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.Model.response.Register.GetOTPResponse;
import com.example.modelfashion.Model.response.Register.RegisterRequest;
import com.example.modelfashion.Model.response.Register.RegisterResponse;
import com.example.modelfashion.Model.response.Register.VerifyOTPRequest;
import com.example.modelfashion.Model.response.Register.VerifyOTPResponse;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.product.ProductResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Single;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Single<CategoryResponse> getCategory(@Url String url);

    @GET
    Single<ProductResponse> getProductByCategory(@Url String url);

    @GET("/product/getAll")
    Single<ArrayList<MyProduct>> getAllProduct();

    @FormUrlEncoded
    @POST("insert_user.php")
    Call<String> insertUser(@Field("taikhoan") String taikhoan,
                            @Field("matkhau") String matkhau);
    @POST("user/login")
    Single<LoginResponse> login(@Body LoginRequest request);

    @POST("user/send-otp")
    Single<GetOTPResponse> getOTP(@Body GetOTPRequest request);

    @POST("user/verify-otp")
    Single<VerifyOTPResponse> verifyOTP(@Body VerifyOTPRequest request);

    @POST("user/register")
    Single<RegisterResponse> register(@Body RegisterRequest request);

    @GET("get_product_by_id.php")
    Single<MyProduct> getProductById(
            @Query("product_id") String productId,
            @Query("product_name") String productName
    );

    @GET("get_all_products_by_type.php")
    Single<ArrayList<MyProduct>> getProductByType(
            @Query("type") String type
    );

    @POST("check_login_user.php")
    Single<Unit> login(@Query("taikhoan") String tk,
                       @Query("matkhau") String mk);
    @FormUrlEncoded
    @POST("update_user_info.php")
    Call<String> UpdateUser(@Field("user") JSONObject user);

}
