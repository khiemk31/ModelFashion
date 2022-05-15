package com.example.modelfashion.network;

import com.example.modelfashion.Model.request.GetProductByCategoryRequest;
import com.example.modelfashion.Model.response.User.User;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.category.DataAllCategory;
import com.example.modelfashion.Model.response.category.MyCategory;
import com.example.modelfashion.Model.response.my_product.DataProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.ProductByCategory;
import com.example.modelfashion.Model.response.product.ProductResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @FormUrlEncoded
    @POST("/user/login")
    Call<User> checkLogin(@Field("phone") String phone,
                          @Field("password") String password);

    @GET("get_product_by_id.php")
    Single<MyProduct> getProductById(
            @Query("product_id") String productId,
            @Query("product_name") String productName
    );

    @GET("product/getProductByCategory/{id}")
    Single<DataProduct> getProductByCategory(@Path("id") String categoryId);

    @POST("check_login_user.php")
    Single<Unit> login(@Query("taikhoan") String tk,
                       @Query("matkhau") String mk);
    @FormUrlEncoded
    @POST("update_user_info.php")
    Call<String> UpdateUser(@Field("user") JSONObject user);

    @GET("category/getAll")
    Single<DataAllCategory> getAllCategory();

}
