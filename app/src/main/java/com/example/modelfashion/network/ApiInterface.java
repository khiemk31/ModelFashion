package com.example.modelfashion.network;

import com.example.modelfashion.Model.request.CreateBillRequest;
import com.example.modelfashion.Model.request.GetProductByPriceRequest;
import com.example.modelfashion.Model.response.Login.ForgotPasswordRequest;
import com.example.modelfashion.Model.response.Login.ForgotPasswordResponse;
import com.example.modelfashion.Model.response.Login.LoginRequest;
import com.example.modelfashion.Model.response.Login.LoginResponse;
import com.example.modelfashion.Model.response.MyProductDetail;
import com.example.modelfashion.Model.response.Register.GetOTPRequest;
import com.example.modelfashion.Model.response.Register.GetOTPResponse;
import com.example.modelfashion.Model.response.Register.RegisterRequest;
import com.example.modelfashion.Model.response.Register.RegisterResponse;
import com.example.modelfashion.Model.response.Register.VerifyOTPRequest;
import com.example.modelfashion.Model.response.Register.VerifyOTPResponse;
import com.example.modelfashion.Model.response.User.UpdateUserRequest;
import com.example.modelfashion.Model.response.User.UpdateUserResponse;
import com.example.modelfashion.Model.response.User.UserDetailResponse;
import com.example.modelfashion.Model.response.bill.Bill;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.category.DataAllCategory;
import com.example.modelfashion.Model.response.my_product.DataProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Single<CategoryResponse> getCategory(@Url String url);

    @GET("/product/getAll")
    Single<DataProduct> getAllProduct();

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

    @GET("user/detail/{user_id}")
    Single<UserDetailResponse> getUserDetail(@Path("user_id") String useID);

    @PUT("/user/update")
    Single<UpdateUserResponse> updateUser(@Query("user_id") String useID, @Body UpdateUserRequest request);

    @PUT("/user/recoveryPass")
    Single<ForgotPasswordResponse> changePassword(@Body ForgotPasswordRequest request);

    @GET("get_product_by_id.php")
    Single<MyProduct> getProductById(
            @Query("product_id") String productId,
            @Query("product_name") String productName
    );

    @GET("product/getProductByCategory/{id}")
    Single<DataProduct> getProductByCategory(
            @Path("id") String categoryId
//            @Path()
    );

    @POST("check_login_user.php")
    Single<Unit> login(@Query("taikhoan") String tk,
                       @Query("matkhau") String mk);

    @FormUrlEncoded
    @POST("update_user_info.php")
    Call<String> UpdateUser(@Field("user") JSONObject user);

    @GET("category/getAll")
    Single<DataAllCategory> getAllCategory();

    @GET("bill/getListBill/{id}")
    Single<Bill> getAllBill(@Path("id") String userID);

    @GET("product/detail/{id}")
    Single<MyProductDetail> getProductDetail(@Path("id") String productId);

    @POST("bill/add")
    Single<ForgotPasswordResponse> createBill(@Body CreateBillRequest request);

    @GET("product/getAll")
    Single<DataProduct> getAll();


    @POST("product/getProductByPrice")
    Single<List<MyProductByCategory>> getProductByPrice(
            @Body GetProductByPriceRequest request
    );
}
